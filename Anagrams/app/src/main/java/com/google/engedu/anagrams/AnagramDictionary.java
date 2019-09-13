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
import java.util.List;
import java.util.Objects;
import java.util.Random;

class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordsList = new ArrayList<>();
    private HashMap<String, ArrayList<String>> lettersToWord
            = new HashMap<>();
    private HashSet<String> wordSet = new HashSet<>();

    AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
//            wordsList.add(word);
            addWordsToMap(word);
        }
    }

    private void addWordsToMap(String word) {
        wordSet.add(word);
        String sortLetters = sortLetters(word);
        if (lettersToWord.containsKey(sortLetters)) {
            Objects.requireNonNull
                    (lettersToWord.get(sortLetters)).add(word);
        } else {
            ArrayList<String> tempList = new ArrayList<>();
            tempList.add(word);
            lettersToWord.put(sortLetters, tempList);
        }
    }

    boolean isGoodWord(String word, String base) {
        if (!wordSet.contains(word))
            return false;
        // TO check if the base string is part of the word
        if (word.contains(base))
            return false;
        return true;
    }

    private List<String> getAnagrams(String targetWord) {
//        ArrayList<String> result = new ArrayList<String>();
//        for (String word : wordsList) {
//            if (isAnagram(word, targetWord)) {
//                result.add(word);
//            }
//        }
//        return result;
        // TODO: What happened here? Ask this next week
        //his part is to cancel the effect of pressing 'enter' when no Anagram word is entered in the given tab
        if (targetWord == null || targetWord.equals(""))
            return null;

        String letters = sortLetters(targetWord);
        if (lettersToWord.containsKey(letters))
            return lettersToWord.get(letters);
        return null;
    }

    private static boolean isAnagram(String first, String second) {
        if (first == null || first.equals("") || second == null
            || second.equals(""))
                return false;

        if (first.length() != second.length())
            return false;

        return sortLetters(first).equals(sortLetters(second));
    }

    private static String sortLetters(String input) {
        char[] res = input.toCharArray();
        Arrays.sort(res);
        return String.valueOf(res);
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for (char c = 'a'; c <= 'z'; c++) {
            List<String> temp = getAnagrams(word + c);
            if (temp != null)
                result.addAll(temp);
        }
        return result;
    }

    // TODO: Homework next week
    protected String pickGoodStarterWord() {
          int num1;
        while(true) {
             num1 = random.nextInt(wordsList.size());
            String value = sortLetters(wordsList.get(num1));
            if(lettersToWord.get(value).size()> MIN_NUM_ANAGRAMS)
                break;
       }
       return wordsList.get(num1);
    }
}
