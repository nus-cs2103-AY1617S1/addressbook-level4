	public  class Task{
			public String taskDescription;
			
			public Task(String taskDescription){
				this.taskDescription = taskDescription;
			}
			
			public String getDescription(){
				return this.taskDescription;
			}
			
			public void setDescription(String taskDescription){
				this.taskDescription = taskDescription;
			}
		}