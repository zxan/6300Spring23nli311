Requirements
When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet).  

The functionality of screens of the following screens: main menu and each sub-screen current job details, job offers, adjust the comparison settings and compare job offers, will not be shown in the design but will be handled in the GUI in the activity_main.xml file and subsequent screen xml files.

When choosing to enter current job details, a user will:
Be shown a user interface to enter (if it is the first time) or edit all the details of their current job, which consist of:
Title
Company
Location (entered as city and state)
Cost of living in the location (expressed as an index)
Yearly salary
Yearly bonus
Restricted Stock Unit Award (expressed as a lump sum vested over 4 years)
Relocation stipend (A single value from $0 to $25,000)
Personal Choice Holidays (A single overall number of days from 0 to 20)

I have added the Current_Job class with the attributes and added a variable and method score to later be able to sort the jobs from best to worst. Current_Job is a child class of the Job class and I have added a boolean current_job to track if the particular job is current. Title, company, location, cost_of_living_in_location, yearly_salarly, yearly_bonus, restricted_stock_unit_award, relocated_stipend, personal_choice_holidays are variables with corresponding setter function from the parent class.

Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.
		
Screen to save, cancel, exit without saving, and returning to the main menu is not shown in the design as it will be implemented in the GUI. After saving the job, the job will be automatically added to the Job_List class. This class contains all the job offers and current job.

When choosing to enter job offers, a user will:
Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.

Job_Offer is a child class of Job and will inherit all variables and methods from the parent. Title, company, location, cost_of_living_in_location, yearly_salarly, yearly_bonus, restricted_stock_unit_award, relocated_stipend, personal_choice_holidays are variables with corresponding setter function from the parent class. User interface to enter all the details of the offer is not shown in design because it will be implemented in the GUI.

Be able to either save the job offer details or cancel.

User interface to be able to either save the job offer details or cancel is not shown in design because it will be implemented in the GUI. After saving the job, the job will be automatically added to the Job_List class. This class contains all the job offers and current job.

Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).

User interface to be able to either save the job offer details or cancel is not shown in design because it will be implemented in the GUI.

When adjusting the comparison settings, the user can assign integer weights to:
Yearly salary
Yearly bonus
Restricted Stock Unit Award 
Relocation stipend 
Personal Choice Holidays 
If no weights are assigned, all factors are considered equal.

I created Class Comparsion_Settings with the variables to track the integer weights and a setter change_weight function.

When choosing to compare job offers, a user will:
Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.

I created the class Compare_Job_Offers with a function print_jobs to print the job list. I created a Job_List class with a function sort to list the best to worst based on the job_score variable.

Select two jobs to compare and trigger the comparison.

When the two jobs are selected from the list, they will be sorted in variables job1 and job2. The comparison will be done in the Class Compare_Job_Offers function compare, passing the two job variables and Comparison_Settings as parameters to calculate.

Be shown a table comparing the two jobs, displaying, for each job:
Title
Company
Location 
Yearly salary adjusted for cost of living
Yearly bonus adjusted for cost of living
Restricted Stock Unit Award 
Relocation stipend 
Personal Choice Holidays 

The compare function will take the parameters to perform the calculations and return string. The string value will include the attributes for each job. The check_if_at_least_two_job function will need to return true for the compare function to run.

Be offered to perform another comparison or go back to the main menu.

User interface to be offered to perform another comparison or go back to the main menu is not shown in design because it will be implemented in the GUI.

When ranking jobs, a job’s score is computed as the weighted sum of:

AYS + AYB + (RSUA / 4) + RELO + (PCH * AYS / 260)

where:
AYS = yearly salary adjusted for cost of living
AYB = yearly bonus adjusted for cost of living
RSU = restricted stock unit award
RELO = relocation stipend
PCH = personal choice holidays

For example, if the weights are 2 for the yearly salary, 2 for relocation stipend and 1 for all other factors, the score would be computed as:

2/7 * AYS + 1/7 * AYB + 1/7 * (RSUA / 4) + 2/7 * RELO + 1/7 * (PCH * AYS / 260)

The job score will be calculated using the Job class function calculate_job_score. The function will take Comparison Settings as a parameter to adjust for any weights. The Comparison_Settings has the function are_weight_equal to return a boolean value to determine if weights are necessary.

The user interface must be intuitive and responsive.

must be intuitive and responsive is not shown in design because it will be implemented in the GUI.

For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).

This design is created with only one user in mind. Data for the jobs will be stored in a list contained within the Job_List class.
