package ontologymapping;

import fr.inrialpes.exmo.align.impl.method.ClassStructAlignment;
import fr.inrialpes.exmo.align.impl.method.NameAndPropertyAlignment;
import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentProcess;
import fr.inrialpes.exmo.align.impl.method.StringDistAlignment;
import fr.inrialpes.exmo.align.impl.method.StrucSubsDistAlignment;
import fr.inrialpes.exmo.align.impl.renderer.RDFRendererVisitor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.AlignmentVisitor;

public class OntologyMapDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            URI owl1 = new URI(Paths.get("").toAbsolutePath()
                    .toUri().toString() + "res/sigkdd.owl");
            URI owl2 = new URI(Paths.get("").toAbsolutePath()
                    .toUri().toString() + "res/OpenConf.owl");
            Scanner scan = new Scanner(System.in);
            AlignmentProcess ap = null;
            while(true) { 
                System.out.println("1. StringDistAlignment\n"
                        + "2. NameAndPropertyAlignment\n"
                        + "3. ClassStructAlignment\n"
                        + "4. StrucSubsAlignment\n"
                        + "5. Exit");
                System.out.print("Enter your selection: ");
                int choice = scan.nextInt();
                
                switch(choice) {
                    case 1:
                        ap = new StringDistAlignment();
                        ontologyAlignment(ap, owl1, owl2, "StringDistAlignment");
                        break;
                    case 2:
                        ap = new NameAndPropertyAlignment();
                        ontologyAlignment(ap, owl1, owl2, "NameAndPropertyAlignment");
                        break;
                    case 3:
                        ap = new ClassStructAlignment();
                        ontologyAlignment(ap, owl1, owl2, "ClassStructAlignment");
                        break;
                    case 4:
                        ap = new StrucSubsDistAlignment();
                        ontologyAlignment(ap, owl1, owl2, "StrucSubsDistAlignment");
                        break;
                    case 5:
                        System.exit(0);
                        break;
                    default:
                        break;
                }
                
                System.out.println();
            }          
        } catch (URISyntaxException | FileNotFoundException ex) {
            Logger.getLogger(OntologyMapDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    private static void ontologyAlignment(AlignmentProcess ap, URI owl1, URI owl2, String type)
            throws FileNotFoundException {
        try {
            ap.init(owl1, owl2);
            ap.align((Alignment) null, new Properties());
            outputResults(type + ".rdf", ap);
        } catch (AlignmentException ex) {
            Logger.getLogger(OntologyMapDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void outputResults(String fileName, AlignmentProcess ap)
            throws FileNotFoundException {
        try {
            PrintWriter writer = new PrintWriter(new File(fileName));
            AlignmentVisitor renderer = new RDFRendererVisitor(writer);
            ap.render(renderer);
            writer.flush();
            writer.close();
        } catch (AlignmentException ex) {
            Logger.getLogger(OntologyMapDriver.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
