package pcd.ass1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/*
 * Agente con il compito di recuperare i vari file dalla directory passata in input
 * e caricarli all'interno di una struttura dati condivisa
 * implementato un producer-consumer tra questo e i worker che leggono i vari pdf.
 */

public class GeneratorAgent extends Thread {

    private String directory;
    private PdfFile<File> files;
    private Flag flag;
    private FinishEvent finish;

    public GeneratorAgent(String directory, PdfFile<File> files, Flag stopFlag, FinishEvent finish) {
        this.directory = directory;
        this.files = files;
        this.flag = stopFlag;
        this.setName("Generator Agent");
        this.finish = finish;
    }

    public void run() {
        if (!flag.isStop()) {
            log("Cerco i file nella directory");
            Path path = Paths.get(directory);

            try (Stream<Path> walk = Files.walk(path)) {

                walk.filter(Files::isReadable) // read permission
                        .filter(Files::isRegularFile) // file only
                        .filter(this::isPdf)                    
                        .map(this::toFile)
                        .forEach(doc -> {
                            flag.isStop();
                            if (!flag.isReset()) {
                                log("File trovato" + doc.getName());
                                files.setPdfFile(doc);
                                finish.add();
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (flag.isReset()) {
                files.reset();
            } else {
                log("Finito");
                finish.setGenFinish();
            }
        }
    }

    /*
    private Stream<PDDocument> toPage(PDDocument document) {
        List<PDDocument> allPages = new ArrayList<PDDocument>();
        Splitter splitter = new Splitter();
        try {
            allPages = splitter.split(document);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return allPages.stream();
    }

    private PDDocument toPDDocument(File file) {
        PDDocument doc = new PDDocument();
        try {
            doc = PDDocument.load(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return doc;
    }

    private Stream<String> toText(File file) {
        PDDocument doc = new PDDocument();
        List<PDDocument> allPages = new ArrayList<PDDocument>();
        List<String> pagesText = new ArrayList<>();
        try {
            Splitter splitter = new Splitter();
            PDFTextStripper stripper = new PDFTextStripper();
            doc = PDDocument.load(file);
            allPages = splitter.split(doc);
            for (PDDocument page: allPages) {
                pagesText.add(stripper.getText(page));
            }
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pagesText.stream();
    }*/

    private File toFile(Path path) {
        return path.toFile();
    }

    private boolean isPdf(Path path) {
        boolean cond = path.getFileName().toString().toLowerCase().endsWith("pdf");
        return cond;
    }

    private void log(String string) {
        System.out.println("[File Manager] " + string);
    }
}