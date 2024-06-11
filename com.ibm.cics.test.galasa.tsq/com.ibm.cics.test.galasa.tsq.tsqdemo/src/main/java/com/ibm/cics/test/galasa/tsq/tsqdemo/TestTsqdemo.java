package com.ibm.cics.test.galasa.tsq.tsqdemo;

import static org.assertj.core.api.Assertions.*;

import dev.galasa.Test;
import dev.galasa.core.manager.*;
import dev.galasa.Test;
import dev.galasa.cicsts.CicsRegion;
import dev.galasa.cicsts.CicsTerminal;
import dev.galasa.cicsts.ICicsRegion;
import dev.galasa.cicsts.ICicsTerminal;
import dev.galasa.core.manager.CoreManager;
import dev.galasa.core.manager.ICoreManager;
import dev.galasa.zos3270.ITerminal;
import dev.galasa.zos3270.Zos3270Terminal;
/**
 * A sample galasa test class
 */
@Test
public class TestTsqdemo {

	// Galasa will inject an instance of the core manager into the following field
	@CoreManager
	public ICoreManager core;

	@CicsRegion(cicsTag = "REGION")
	public ICicsRegion region;

	@CicsTerminal(cicsTag = "REGION")
	public ICicsTerminal cicsTerminal;

	/**
	 * Test which demonstrates that the managers have been injected ok.
	 */
	@Test
	public void simpleSampleTest() {
		assertThat(core).isNotNull();
	}

	@Test
	public void mainframeTest() throws Exception{
		cicsTerminal.type("TSQT").enter();

		assertThat(cicsTerminal.searchText("HELLO WORLD")).isTrue();

		cicsTerminal.clear().wfk().type("CECI").enter().waitForKeyboard();


		region.ceci().issueCommand(cicsTerminal, "CECI READQ TS QUEUE('GALASA') INTO(&DATA)");
		String response = region.ceci().retrieveVariableText(cicsTerminal, "DATA");
		assertThat(response).contains("HELLO WORLD");
	}

}
