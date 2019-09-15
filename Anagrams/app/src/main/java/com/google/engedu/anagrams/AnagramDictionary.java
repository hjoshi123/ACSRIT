/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private int wordLength=DEFAULT_WORD_LENGTH;
    private Random random = new Random();
    private ArrayList<String> wordsList = new ArrayList<>();
    private HashMap<String, ArrayList<String>> lettersToWord
            = new HashMap<>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<Integer,ArrayList<String>> sizeToWords=new HashMap<>();

    AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordsList.add(word);
            addWordsToMap(word);
        }
        for (String i:wordSet)
        {
            String sorted=sortLetters(i);
            if (lettersToWord.get(sorted).size()>=MIN_NUM_ANAGRAMS)
            {
                if(sizeToWords.containsKey(i.length()))
                    sizeToWords.get(i.length()).add(i);
                else
                {
                    ArrayList<String>temp1=new ArrayList<String>();
                    temp1.add(i);
                    sizeToWords.put(i.length(),temp1);
                }
            }
        }
    }

    private void addWordsToMap(String word) {
        wordSet.add(word);
        String sorted=sortLetters(word);
        if(lettersToWord.containsKey(sorted))
        {
            Objects.requireNonNull(lettersToWord.get(sorted)).add(word);
        }
        else
        {
            ArrayList<String>temp=new ArrayList<String>();
            temp.add(word);
            lettersToWord.put(sorted,temp);
        }
    }

    boolean isGoodWord(String word, String base) {
           if(!wordSet.contains(word))
               return false;
           if(word.contains(base))
               return false;
           return true;
    }

    private List<String> getAnagrams(String targetWord) {
        if(targetWord==null||targetWord.equals(""))
            return null;
        String sorted=sortLetters(targetWord);
        if(lettersToWord.containsKey(sorted))
            return lettersToWord.get(sorted);
        return null;
    }

    private static boolean isAnagram(String first, String second) {
        if (first == null || first.equals("") || second == null
                || second.equals(""))
            return false;

        if (first.length() != second.length())
            return false;
        else return sortLetters(first).equals(sortLetters(second));
    }

    private static String sortLetters(String input) {
        char temp[]=input.toCharArray();
        Arrays.sort(temp);
        return new String(temp);
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
      ArrayList<String>result=new ArrayList<String>();
      for(char c='a';c<='z';c++)
      {
          List<String> temp = getAnagrams(word + c);
          if(temp!=null)
              result.addAll(temp);
      }
      return result;
    }
    //Solved an extension by removing the words that do not have minimum number of anagrams, that is hashmap
    //sizeToWords holds only the words which has satisfied minimum number of anagrams condition, but still
    //the HashSet wordSet has all the words which can be used later...
    protected String pickGoodStarterWord() {
        String t;
        while (true)
        {
            if(sizeToWords.containsKey(wordLength))
            {
                t=sizeToWords.get(wordLength).get(random.nextInt(sizeToWords.get(wordLength).size()));
                if (wordLength<MAX_WORD_LENGTH)
                wordLength++;
                return t;
            }
            else
                wordLength++;
        }
    }
}
