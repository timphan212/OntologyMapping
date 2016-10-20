package ontologymapping;

// Alignment API classes
import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentProcess;
import org.semanticweb.owl.align.AlignmentVisitor;

// Alignment API implementation classes
import fr.inrialpes.exmo.align.impl.BasicParameters;
import fr.inrialpes.exmo.align.impl.method.EditDistNameAlignment;
import fr.inrialpes.exmo.align.impl.method.NameAndPropertyAlignment;
import fr.inrialpes.exmo.align.impl.method.StringDistAlignment;
import fr.inrialpes.exmo.align.impl.renderer.HTMLRendererVisitor;

// Java standard classes
import java.io.PrintWriter;
import java.net.URI;
import java.util.Properties;
import java.io.FileNotFoundException;
import org.semanticweb.owl.align.AlignmentException;

/**
 * The Skeleton of code for embeding the alignment API
 *
 * Takes two files as arguments and align them.
 */
public class Skeleton {

    private final URI onto1;
    private final URI onto2;

    public Skeleton(URI _onto1, URI _onto2) {
        onto1 = _onto1;
        onto2 = _onto2;
    }

    public void match(int matchingTechnique, int outputFormat) {

        if (matchingTechnique == MatchingTechniques.ALL) {      // repeat match for all techiniques
            for (int i = 1; i <= MatchingTechniques.TOTALTECHNIQUES; i++) {
                match(i, outputFormat);
            }
        } else {
            try {

                // Aligning
                AlignmentProcess alignment;
                if (matchingTechnique == MatchingTechniques.EDITDISTANCENAME) {
                    alignment = new EditDistNameAlignment();
                } else if (matchingTechnique == MatchingTechniques.NAMEANDPROPERTY) {
                    alignment = new NameAndPropertyAlignment();
                } else {        // default
                    alignment = new StringDistAlignment();
                }

                Properties params = new BasicParameters();

                // match ontologies
                alignment.init(onto1, onto2);
                alignment.align((Alignment) null, params);

                // select output file extension
                String fileName = "result";
                if (outputFormat == OutputFormat.HTML) {
                    fileName = fileName + matchingTechnique + ".html";
                } else if (outputFormat == OutputFormat.CSV) {
                    fileName = fileName + matchingTechnique + ".csv";
                } else if (outputFormat == OutputFormat.RDF) {
                    fileName = fileName + matchingTechnique + ".rdf";
                }

                try ( // write match results
                        PrintWriter writer = new PrintWriter(fileName)) {
                    AlignmentVisitor renderer = new HTMLRendererVisitor(writer);

                    alignment.render(renderer);
                    writer.flush();
                }

            } catch (AlignmentException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
