import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import services.I18n;

import java.text.ChoiceFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.Month;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class Survey implements Consumer<TextIO> {

    public static void main(String[] args) {
        TextIO textIO = TextIoFactory.getTextIO();
        Locale currentLocale = I18n.getLocale();
        String language = currentLocale.getLanguage();
        String country = currentLocale.getCountry();

        if (args.length == 1) {
            language = args[0];
        } else if (args.length == 2) {
            language = args[0];
            country = args[1];
        }

        var locale = new Locale(language, country);
        if (!I18n.isSupported(locale)) {
            System.err.println("Specified Locale is not supported: " + locale.toString());
        }
        I18n.setLocale(locale);

        new Survey().accept(textIO);
    }

    @Override
    public void accept(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        double[] questionLimits = {1,2};
        String [] questionStrings = {
                I18n.getMessage("OneQuestion"),
                I18n.getMessage("MultipleQuestions")
        };
        ChoiceFormat choiceForm = new ChoiceFormat(questionLimits, questionStrings);
        String pattern = I18n.getMessage("QuestionsLeftPattern");
        I18n.applyPattern(pattern, choiceForm);

        terminal.println(I18n.getPatternMessage(new Object[]{2, 2}));

        String name = textIO.newStringInputReader()
                .read(I18n.getMessage("Username"));

        int age = textIO.newIntInputReader()
                .withMinVal(13)
                .read(I18n.getMessage("Age"));

        String email = textIO.newStringInputReader()
                .read(I18n.getMessage("Email"));

        String question1 = textIO.newStringInputReader()
                .read(I18n.getMessage("Question1"));

        String question2 = textIO.newStringInputReader()
                .read(I18n.getMessage("Question2"));

        String question3 = textIO.newStringInputReader()
                .read(I18n.getMessage("Question3"));

        terminal.println("\n\nSURVEY OVERVIEW");
        terminal.println("==============\n");
        terminal.printf("%s: %s\n", I18n.getMessage("Username"), name);
        terminal.printf("%s: %s\n", I18n.getMessage("Email"), email);
        terminal.printf("%d: %d\n", I18n.getMessage("Age"), age);
        terminal.printf("%s: %s\n", I18n.getMessage("Question1"), question1);
        terminal.printf("%s: %s\n", I18n.getMessage("Question2"), question2);
        terminal.printf("%s: %s\n", I18n.getMessage("Question3"), question3);

        textIO.newStringInputReader().withMinLength(0).read(I18n.getMessage("Abort"));
        textIO.dispose();
    }
}