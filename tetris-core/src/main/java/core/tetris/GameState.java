package core.tetris;

public class GameState {

    private static final int DEFAULT_DELAY = 750;

    private int totalLines;
	private int delay;
	private int score;
	private boolean playing;

	public void updateDelay() {
		if (totalLines == 10)
			delay = 500;
		if (totalLines == 20)
			delay = 400;
		if (totalLines == 30)
			delay = 300;
		if (totalLines == 40)
			delay = 250;
		if (totalLines == 50)
			delay = 120;
	}

	public void reset() {
		totalLines = 0;
		score = 0;
		delay = DEFAULT_DELAY;
		playing = false;
	}

	public void incrementTotalLinesBy(int completedLines) {
		totalLines += completedLines;
	}

	public int getScore() {
        return score;
	}

	public void updateScore(int completeLines) {
		score += completeLines * completeLines * 100;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void stopPlaying() {
		playing = false;
	}

	public void startPlaying() {
		playing = true;
	}

    public long getDelay() {
        return delay;
    }
}