package seedu.address.model.task;

public interface FavoritableTask {
	boolean isFavorite = false;
	
	void setIsFavorite(boolean isFavorite);
	boolean getIsFavorite();
}
