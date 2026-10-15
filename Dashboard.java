package com.playwright.tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;

public class Dashboard {
	
	public static void main(String[] args)
	{
		Login_Page p = new Login_Page();
		Page  page = p.login();
		
		page.waitForTimeout(7000);
	
		//Verify login has done
		if(page.locator("//div[@class='oxd-brand-banner']").isVisible())
		{
			// Store the original tab
	        Page originalPage = page;
	        
			int value = page.locator(".orangehrm-dashboard-widget-body").count();
			System.out.println("Value : "+ value);
			if(value == 7)
			{
			 String[] headings = {
					    "//h6[text()='Assign Leave']",
					    "//h5[text()='Leave List']",
					    "//h6[text()='Select Employee']",
					    "Apply Leave",
					    "My Leave List",
					    "My Timesheet"
					};
			 String[] pageNames = {
					    "Assign Leave",
					    "Leave List",
					    "Timesheets",
					    "Apply Leave",
					    "My Leave List",
					    "My Timesheet"
					};

					for (int i = 1; i <= 6; i++) {

					    // Click dashboard quick-launch button
					    page.locator(
					        "(//button[@class='oxd-icon-button orangehrm-quick-launch-icon'])[" + i + "]"
					    ).click();

					    Locator heading;
					    if (i <= 3) {
					        // First three are XPath locators
					        heading = page.locator(headings[i - 1]);
					    } else {
					        // Last three are text locators
					        heading = page.getByText(
					            headings[i - 1],
					            new Page.GetByTextOptions().setExact(true)
					        );
					    }
					    try {
					        heading.waitFor(
					            new Locator.WaitForOptions()
					                .setState(WaitForSelectorState.VISIBLE)
					                .setTimeout(10000)
					        );

					        System.out.println(pageNames[i - 1] + " page is visible");

					    } catch (PlaywrightException e) {
					        System.err.println(pageNames[i - 1] + " page is not visible");
					    }

					    // Return to dashboard
					    page.goBack();
					    
					}
					  //Open help
					page.getByTitle("Help").click();
				
					System.out.println("New tab opened: " + page.url());

					// Switch back to the previous tab
					originalPage.bringToFront();

					System.out.println("Back to original tab: " + originalPage.url());
			}
			else
			{
				System.err.println("Something is missing from dashboard");
			}
		}
		else
		{
			System.err.println("Login is not done successfully.");
		}	
		
		page.close();
		
	}

}
