package com.carbontest.treasuremap.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class configLoaderTest {
	 
	@MockBean
	private ConfigLoader mockConfigLoader;

	@BeforeEach
	public void testSetup() {
		 String mockFileContent = "# {C comme Carte} - {Nb. de case en largeur} - {Nb. de case en hauteur}\r\n"
				 + "# {M comme Montagne} - {Axe horizontal} - {Axe vertical}\r\n"
				 + "# {T comme Trésor} - {Axe horizontal} - {Axe vertical} - {Nb. de trésors}\r\n"
				 + "# {A comme Aventurier} - {Nom de l’aventurier} - {Axe horizontal} - {Axevertical} - {Orientation} - {Séquence de mouvement}\r\n"
				 + "C - 5 - 5\r\n"
				 + "M - 2 - 3\r\n"
				 + "M - 3 - 2\r\n"
				 + "T - 1 - 2 - 3\r\n"
				 + "T - 1 - 3 - 3\r\n"
				 + "A - Jean - 1 - 1 - S - AAGGADAADADDAD\r\n"
				 + "A - Jules - 2 - 1 - S - AAAGAADAADAA";
        try ( InputStream inputStream = new ByteArrayInputStream(mockFileContent.getBytes(StandardCharsets.UTF_8))){
        	when(this.mockConfigLoader.loadResource()).thenReturn(inputStream);
        	when(this.mockConfigLoader.getMapString()).thenCallRealMethod();
            when(this.mockConfigLoader.getMapParameters()).thenCallRealMethod();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	 
	 @Test
	 public void loadResourceAndConvertToInputStream() {
		 InputStream inputStream = this.mockConfigLoader.loadResource();
		 assertNotNull(inputStream,"inputStream should not be null after loading of resource");
	 }
	 
	 @Test
	 public void convertLoaderInputStreamToString() {
		 String mapAsString = this.mockConfigLoader.getMapString();
		 assertEquals("# {C comme Carte} - {Nb. de case en largeur} - {Nb. de case en hauteur}\r\n"
				 + "# {M comme Montagne} - {Axe horizontal} - {Axe vertical}\r\n"
				 + "# {T comme Trésor} - {Axe horizontal} - {Axe vertical} - {Nb. de trésors}\r\n"
				 + "# {A comme Aventurier} - {Nom de l’aventurier} - {Axe horizontal} - {Axevertical} - {Orientation} - {Séquence de mouvement}\r\n"
				 + "C - 5 - 5\r\n"
				 + "M - 2 - 3\r\n"
				 + "M - 3 - 2\r\n"
				 + "T - 1 - 2 - 3\r\n"
				 + "T - 1 - 3 - 3\r\n"
				 + "A - Jean - 1 - 1 - S - AAGGADAADADDAD\r\n"
				 + "A - Jules - 2 - 1 - S - AAAGAADAADAA",
				 mapAsString,
				 "Converted inputStream as string is not equal to what is expected");
	 }
	 
	 @Test
	 public void convertStringToParameters() {
		 List<String> listParameters = this.mockConfigLoader.getMapParameters();
		 assertTrue(listParameters.size()==7,"The list of parameters read by configLoader should have 7 elements inside");
		 assertTrue(listParameters.get(2).split("-").length==3,"The second line of parameters read by configLoader should have 3 elements inside");
		 assertEquals("C-5-5",listParameters.get(0),"Element is not what expected");
		 assertEquals("A-Jean-1-1-S-AAGGADAADADDAD",listParameters.get(5),"Element is not what expected");
	 }

}
