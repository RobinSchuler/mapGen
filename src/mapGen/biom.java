package mapGen;
import java.io.IOException;

public class biom {
    public static void main(String args[])
    {
    	/* shot at making a custom world generation by randomly dividing into smaller rectangles
    	 * the deeper the recursion the less difference. Result was not the best as there were clear lines
    	 * at the first dividing. Alternatives are Diamond-Square Algo or Simplex Noise
    	 * 
    	 * 
    	 * */
    	
    	int worldWidth = 30;
    	int worldHeight = 30;
    	int[][] worldHeightMap = new int[worldWidth][worldHeight];
    	int[][] heatMap = new int[worldWidth][worldHeight];
    	int[][] rainMap = new int[worldWidth][worldHeight];
    	
    	//heightmap
    	partWorld(0,0,worldWidth, worldHeight, 5, 1, 0, 10, worldHeightMap, 1, 0, 0.3);
    	partWorld(0,0,worldWidth, worldHeight, 50, 20, 0, 100, heatMap, 5, 5, 1);
    	partWorld(0,0,worldWidth, worldHeight, 50, 20, 0, 100, rainMap, 5, 5, 1);
    	System.out.println("############################# Height Map ##################################");
    	for(int i = 0;i<worldHeight;i++)
    	{
        	for(int j = 0;j<worldWidth;j++)
        	{
        		System.out.println("row=" + i + " col= " + j + " value = " + worldHeightMap[i][j]);
        	}
    	}
    	System.out.println("############################# Height Map ##################################");
    	System.out.println("############################# Heat Map ##################################");
    	for(int i = 0;i<worldHeight;i++)
    	{
        	for(int j = 0;j<worldWidth;j++)
        	{
        		System.out.println("row=" + i + " col= " + j + " value = " + heatMap[i][j]);
        	}
    	}
    	System.out.println("############################# Heat Map ##################################");
    	System.out.println("############################# Rain Map ##################################");
    	for(int i = 0;i<worldHeight;i++)
    	{
        	for(int j = 0;j<worldWidth;j++)
        	{
        		System.out.println("row=" + i + " col= " + j + " value = " + rainMap[i][j]);
        	}
    	}
    	System.out.println("############################# Rain Map ##################################");
    }
    
    private static int generateBetween (int min, int max, double probability)
    {
    	
    	int diff = max-min;
    	int value = (int)(Math.round(Math.random()*diff))+min;
    	boolean shouldUse = (Math.random()*100<probability*100);
    	if(shouldUse)
    		return value;
    	return 0;
    }
    
    private static void partWorld(int startX, int startY, int width, int height, int setting, int maxDiffSetting, int minSetting, int maxSetting, int[][] result, int minDiff, int diffMinusPerIteration, double probability)
    {
    	int cappedSetting = setting;
    	if(setting < minSetting)
    		cappedSetting = minSetting;
    	else if(setting > maxSetting)
    		cappedSetting = maxSetting;
    	int nextMaxDiffSetting = Math.max(minDiff, maxDiffSetting-diffMinusPerIteration);
    	if(width==1 && height == 1)
    	{
    		//write to result array at pos
    		result[startX][startY]=cappedSetting;
    	}
    	else if (width == 1)
    	{
    		//part height in 2
    		int randY = generateBetween(1, height-1, 1.0);
    		partWorld(startX, startY, 1, randY, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
    		partWorld(startX, startY+randY, 1, height-randY, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
    	}
    	else if (height == 1)
    	{
    		//part width in 2
    		int randX = generateBetween(1, width-1, 1.0);
    		partWorld(startX, startY, randX, 1, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
    		partWorld(startX+randX, startY, width-randX, 1, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
    	}
    	else
    	{
    		int randX = generateBetween(1, width-1, 1.0);
    		int randY = generateBetween(1, height-1, 1.0);
    		partWorld(startX, startY, randX, randY, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
    		partWorld(startX+randX, startY, width-randX, randY, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
    		partWorld(startX, startY+randY, randX, height-randY, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
    		partWorld(startX+randX, startY+randY, width-randX,  height-randY, cappedSetting + generateBetween(maxDiffSetting*-1, maxDiffSetting, probability),nextMaxDiffSetting, minSetting, maxSetting, result, minDiff, diffMinusPerIteration, probability);
    	}
    }
}
