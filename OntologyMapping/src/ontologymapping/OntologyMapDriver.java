package ontologymapping;

import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentProcess;
import fr.inrialpes.exmo.align.impl.method.StringDistAlignment;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.owl.align.AlignmentException;

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

            AlignmentProcess ap1 = new StringDistAlignment();
            Properties props = new Properties();
            props.setProperty("stringFunction", "smoaDistance");
            ap1.init(owl1, owl2);
            ap1.align((Alignment) null, props);
            
            props = new Properties();
            props.setProperty("stringFunction", "ngramDistance");
            AlignmentProcess ap2 = new StringDistAlignment();
            ap2.init(owl1, owl2);
            ap2.align((Alignment) null, props);
        } catch (URISyntaxException | AlignmentException ex) {
            Logger.getLogger(OntologyMapDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
