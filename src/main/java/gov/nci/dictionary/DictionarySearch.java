package gov.nci.dictionary;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import gov.nci.Utilities.ScrollUtil;
import gov.nci.framework.PageObjectBase;

public class DictionarySearch extends PageObjectBase {
    // static String tier = "DT";


    public DictionarySearch (WebDriver browser) throws MalformedURLException, UnsupportedEncodingException {
        super(browser);
        PageFactory.initElements(browser, this);
    }

    /**************** Sitewide Search Results Page Elements *****************************/
    @FindBy(css = "h1")
    WebElement txt_header;
    @FindBy(css = "div span.radio")
    WebElement contains_toggle;
    @FindBy(css = "#radioStarts" )
    WebElement btn_startswith;
    @FindBy(css = "#radioContains" )
    WebElement btn_contains;
    @FindBy(css = "input.dictionary-search-input" )
    WebElement search_input;
    @FindBy(css = "input.button" )
    WebElement search_btn;
    @FindBy(css = "div.az-list" )
    WebElement az_list;
    @FindBy(css = "span.results-count" )
    WebElement results_home;
    @FindBy(css = "div.az-list ul li a")
    List<WebElement> az_list_letters;
    @FindBy(css = "div.results dfn span")
    WebElement defHeader;
    /**************** Sitewide Search Results Page Elements *****************************/


    // Testing if the H1 header element exists on the page
    // ---------------------------------------------------
    public boolean isHeaderVisible() {
        return txt_header.isDisplayed();
    }


    // Getting the H1 header text
    // ---------------------------------------------------
    public String getHeaderText() {
        return txt_header.getText();
    }


    // Testing if the radio button for the StartsWith/Contains selection is displayed
    // ------------------------------------------------------------------------------
    public boolean isRadioBtnVisible() {
        return contains_toggle.isDisplayed();
    }


    // Testing if the right URL is listed of the A-Z list when element is clicked and
    // returning a non-zero result  J9 O14 Q16 Y24 #26
    // ------------------------------------------------------------------------------
    public boolean isAZListUrlOK(WebDriver driver, String dictionary,
                                                   String language) {
        int curCount = 0;
        // int allCount = az_list_letters.size();
        String[] urlParts;
        String urlExpected;

        // Loop through the alphabet and test the URL displays the correct letter
        // ----------------------------------------------------------------------
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            System.out.print(az_list_letters.get(curCount).getText() + " ");
            az_list_letters.get(curCount).click();
            urlParts = driver.getCurrentUrl().split("[?]");  // '?' is special character
            urlExpected = "expand=" + letter;

            // If the URL doesn't match the expected value return false
            // --------------------------------------------------------
            if (!urlParts[1].equals(urlExpected)){
                return false;
            }

            curCount++;
        }

        // All dictionaries also have an entry for '#' and some include 'All'
        // ------------------------------------------------------------------
        String[] extra = new String[2];
        extra[0] = "%23";  // Hash tag
        extra[1] = "All";

        for (int i = 0; i < 2; i++) {
            if (i == 1 && dictionary == "glossary" || dictionary == "glossaryES") {
                continue;
            }

            System.out.print(az_list_letters.get(curCount).getText() + " ");
            az_list_letters.get(curCount).click();
            urlParts = driver.getCurrentUrl().split("[?]");  // '?' is special character
            urlExpected = "expand=" + extra[i];

            // If the URL doesn't match the expected value return false
            // --------------------------------------------------------
            if (!urlParts[1].equals(urlExpected)){
                return false;
            }
            curCount++;
        }
        return true;
    }

}
