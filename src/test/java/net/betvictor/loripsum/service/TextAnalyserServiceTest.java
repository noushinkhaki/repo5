package net.betvictor.loripsum.service;

import net.betvictor.loripsum.model.TextAnalyserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TextAnalyserServiceTest {

    private static final TextAnalyserService service = new TextAnalyserService();

    private static final String FREQUENT_EXPECTED_WORD = "satis";

    Map<Integer, String> textMap;

    @BeforeEach
    void init() {
        textMap = new HashMap<>();
        textMap.put(0, "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Tibi hoc incredibile, " +
                "quod beatissimum. Bork </p>");
        textMap.put(1, "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ad illum redeo. " +
                "Mihi enim satis est, ipsis non satis. Iam in altera philosophiae parte. Audeo dicere, inquit. " +
                "Bork </p><p>Ergo id est convenienter naturae vivere, a natura discedere. " +
                "Murenam te accusante defenderem. Duo Reges: constructio interrete. " +
                "Qualem igitur hominem natura inchoavit? </p>");
    }

    @Test
    void testGenerateText2() {
        TextAnalyserResponse response = new TextAnalyserResponse();
        response = service.analyseText(textMap, response);
        assertNotNull(response);
        assertEquals(FREQUENT_EXPECTED_WORD, response.getFreq_word());
    }
}
