package gov.nci.webanalyticstests.r4r.pages;

import org.testng.annotations.BeforeClass;

import gov.nci.webanalyticstests.AnalyticsTestLoadBase;

public class R4RLoadBase extends AnalyticsTestLoadBase {

	protected String testDataFilePath;

	// ==================== Setup methods ==================== //

	@BeforeClass(groups = { "Analytics" })
	protected void setupClass() {
		///
	}

}