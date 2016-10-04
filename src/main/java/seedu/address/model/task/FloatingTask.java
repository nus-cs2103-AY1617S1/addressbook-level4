package seedu.address.model.task;

/*
 * A simple Task implementation that does not have a deadline
 */
public class FloatingTask extends Task implements FavoritableTask {

	private boolean isFavorite = false;
	
	@Override
	public void setIsFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
		
	}

	@Override
	public boolean getIsFavorite() {
		return isFavorite;
	}

	
	
}
