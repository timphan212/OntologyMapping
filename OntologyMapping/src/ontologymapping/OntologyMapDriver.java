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

            Skeleton sk = new Skeleton(owl1, owl2);
            sk.match(MatchingTechniques.ALL, OutputFormat.HTML);

        } catch (Exception ex) {
            Logger.getLogger(OntologyMapDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
