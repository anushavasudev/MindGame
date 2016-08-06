import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Games {
    
	static HashMap<String,ArrayList<String>> fourWords;
	static HashMap<String,ArrayList<String>> fiveWords;
	static HashMap<String,ArrayList<String>> sixWords;
	static HashMap<String,ArrayList<String>> repFourWords;
	static HashMap<String,ArrayList<String>> repFiveWords;
	static HashMap<String,ArrayList<String>> repSixWords;
	
	static ArrayList<String> fourWordsList;
	static ArrayList<String> fiveWordsList;
	static ArrayList<String> sixWordsList;
	static ArrayList<String> repFourWordsList;
	static ArrayList<String> repFiveWordsList;
	static ArrayList<String> repSixWordsList;
	static int[] alphabetStatus=new int[26];

    static String computerSecretWord = "BEAR";
    static int won=0;

	public Games()
	{
		fourWords = new HashMap<String,ArrayList<String>>();
		fiveWords = new HashMap<String,ArrayList<String>>();
		sixWords = new HashMap<String,ArrayList<String>>();
		repFourWords = new HashMap<String,ArrayList<String>>();
		repFiveWords = new HashMap<String,ArrayList<String>>();
		repSixWords = new HashMap<String,ArrayList<String>>();
		
		fourWordsList = new ArrayList<String>();
		fiveWordsList = new ArrayList<String>();
		sixWordsList = new ArrayList<String>();
		repFourWordsList = new ArrayList<String>();
		repFiveWordsList = new ArrayList<String>();
		repSixWordsList = new ArrayList<String>();

        initialize();
	}
	
	private static void initialize() {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("C:\\Users\\arprasanna\\Desktop\\sowpods.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String word="";
		try {
			while((word = br.readLine())!=null)	{
				addWordtoMaps(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int noOfCommonLetters(String word1, String word2)
	{
		int[] charArray1 = new int[26];
		int[] charArray2 = new int[26];
		int count = 0;
		for(int i=0;i<26;i++)
		{
			charArray1[i] = 0;
			charArray2[i] = 0;
		}
		for(int i=0;i<word1.length();i++)
			charArray1[word1.charAt(i)-'A']++;
		for(int i=0;i<word2.length();i++)
			charArray2[word2.charAt(i)-'A']++;
		for(int i=0;i<26;i++)
		{
			if((charArray1[i]>0)&&(charArray2[i])>0)
				count++;
		}
		return count;
	}
	
    private static void addWordtoMaps(String word) {
		// TODO Auto-generated method stub
    	
    	String anagram = getAnagram(word);
		if(hasRepChars(anagram))
		{
			if(word.length() == 4)
			{
				addWord(anagram,word,repFourWords);
				repFourWordsList.add(word);
			}
			if(word.length() == 5)
			{
				addWord(anagram,word,repFiveWords);
				repFiveWordsList.add(word);
			}
			if(word.length() == 6)
			{
				addWord(anagram,word,repSixWords);
				repSixWordsList.add(word);
			}
		}
		else
		{
			if(word.length() == 4)
			{
				addWord(anagram,word,fourWords);
				fourWordsList.add(word);
			}
			if(word.length() == 5)
			{
				addWord(anagram,word,fiveWords);
				fiveWordsList.add(word);

			}
			if(word.length() == 6)
			{
				addWord(anagram,word,sixWords);
				sixWordsList.add(word);

			}
		}
	}

    
	private static String getAnagram(String word) {
		// TODO Auto-generated method stub
		char[] charArray = word.toCharArray();
		Arrays.sort(charArray);
		return new String(charArray);
	}


	private static boolean hasRepChars(String anagram) {
		// TODO Auto-generated method stub
		char c,prev;
		prev = anagram.charAt(0);
		for(int i=1;i<anagram.length();i++)
		{
			c = anagram.charAt(i);
			if(c == prev)
				return true;
			prev = c;
		}
		return false;
	}


	private static void addWord(String anagram, String word, HashMap<String, ArrayList<String>> hm) {
		// TODO Auto-generated method stub
		if(hm.containsKey(anagram))
		{
			hm.get(anagram).add(word);
		}
		else
		{
			ArrayList<String> value = new ArrayList<String>();
			value.add(word);
			hm.put(anagram, value);
		}
	}


	public static void generateFirstComputerWord() {
        
    }

    @SuppressWarnings({ "unchecked", "resource" })
	public static void playGame() {
    	String compGivenWord;
        int userInteger = 0;
        ArrayList<String> wordsList = null;
        Scanner sc = new Scanner(System.in);
        while(true)
        {
        	System.out.println("Enter the difficulty level(4,5,6): ");
        	userInteger = sc.nextInt();
        	setWon(0);
        	if(userInteger == 4)
            	wordsList = (ArrayList<String>) fourWordsList.clone();
            else if(userInteger == 5)
            	wordsList = (ArrayList<String>) fiveWordsList.clone();
            else if(userInteger == 6)
            	wordsList = (ArrayList<String>) sixWordsList.clone();
            computerSecretWord = computerGivesWord(wordsList);
            System.out.println("Computer Secret Word: "+computerSecretWord);
            while(!hasWon()) {
                compGivenWord = computerGivesWord(wordsList);
                System.out.println(compGivenWord);
                wordsList = userIntegerInput(compGivenWord,wordsList);
                if(!hasWon())
                	userWordInput();
            }
        }
        
    }
    public static boolean hasWon() {
        return won == 1;
    }
    
    public static void setWon(int i)
    {
        won = i;
    }

    
    @SuppressWarnings("resource")
	public static void userWordInput() {
    	String userWord;
    	int noOfCommonLetters;
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.print("Word : ");
        userWord = reader.nextLine();
        noOfCommonLetters = noOfCommonLetters(userWord,computerSecretWord);
        System.out.println(noOfCommonLetters);
        if((noOfCommonLetters==computerSecretWord.length()) && (userWord.equals(computerSecretWord) )) {
            setWon(1);
            System.out.println("User won. Word was"+userWord);
        }

    }
    
    public static String computerGivesWord(ArrayList<String> wordsList) {
        int randomNumber;
        Random rand = new Random();
        randomNumber = rand.nextInt(wordsList.size());
        return wordsList.get(randomNumber);
    }
    
    @SuppressWarnings("resource")
	public static ArrayList<String>  userIntegerInput(String compGenword, ArrayList<String> wordsList)
    {
    	int score=0;
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Enter the number of matches: ");
    	score = sc.nextInt();
        char[] charArray = compGenword.toCharArray();
        if(score==0)
        {
            for(int i=0;i<charArray.length;i++)
            {
                alphabetStatus[charArray[i]-'A']=0;
            }
            wordsList = removeWordsFromList(compGenword,wordsList);
        }
        else if(score==compGenword.length())
        {
        	wordsList.remove(compGenword);
            wordsList = keepAnagrams(compGenword,wordsList);
        }
        else if(score==-1)
        {
        	System.out.println("Game Over! Bwahahaha! I won.");
        	setWon(1);
        }
        else
        {
        	wordsList.remove(compGenword);
        	wordsList = trimOnCount(compGenword,wordsList,score);
        }
        return wordsList;
    }

    
     private static ArrayList<String> trimOnCount(String compGenword, ArrayList<String> wordsList, int score) {
		// TODO Auto-generated method stub
    	 ArrayList<String> remove = new ArrayList<String>();
    	 for(String word: wordsList)
    	 {
    		 if(noOfCommonLetters(compGenword,word)<score)
    		 {
    			 remove.add(word);
    		 }
    	 }
    	 for(String word: remove)
    	 {
    			 wordsList.remove(word);
    	 }
		return wordsList;
	}

	private static ArrayList<String> keepAnagrams(String compGenword, ArrayList<String> wordsList) {
		// TODO Auto-generated method stub
    	 ArrayList<String> keep = new ArrayList<String>();
    	 String compGenAnagram = getAnagram(compGenword);
    	 for(String word: wordsList)
    	 {
    		 if(getAnagram(word).equals(compGenAnagram))
    		 {
    			 keep.add(word);
    		 }
    	 }
    	 wordsList.clear();
    	 for(String word: keep)
    	 {
    			 wordsList.add(word);
    	 }
		return wordsList;
	}

	private static ArrayList<String> removeWordsFromList(String compGenword, ArrayList<String> wordsList) {
		// TODO Auto-generated method stub
    	 ArrayList<String> remove = new ArrayList<String>();
    	 for(String word: wordsList)
    	 {
    		 if(noOfCommonLetters(compGenword,word)>0)
    		 {
    			 remove.add(word);
    		 }
    	 }
    	 for(String word: remove)
    	 {
    			 wordsList.remove(word);
    	 }
		return wordsList;
	}

	public static void main(String []args) throws FileNotFoundException{
        new Games();
    	//System.out.println(fourWordsList.size());
        playGame();
        
     }

	
}
