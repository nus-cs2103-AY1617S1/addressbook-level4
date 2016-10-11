package seedu.address.ui;
	
public  class UiTaskTest{
			public String taskDescription;
			
			public UiTaskTest(String taskDescription){
				this.taskDescription = taskDescription;
			}
			
			public String getDescription(){
				return this.taskDescription;
			}
			
			public void setDescription(String taskDescription){
				this.taskDescription = taskDescription;
			}
		}