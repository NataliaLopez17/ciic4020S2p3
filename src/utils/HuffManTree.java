package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import map.HashTableSC;
import map.Map;
import map.SimpleHashFunction;
import sortedList.SortedArrayList;
import sortedList.SortedList;

/**
 * This class represents what a HuffmanTree is, how it is constructed and the
 * logic behind it
 * 
 * @author Natalia E. Lopez
 *
 */
public class HuffManTree {

	public static void main(String[] args) {
		String loadData = load_data("stringData6.txt");
		if (loadData == null) {
			System.out.println("The text file cannot be empty.");
		} else {
			Map<String, Integer> computeFD = compute_fd(loadData);
			// uncomment to see the tree
			BTNode<Integer, String> huffManTree = huffman_tree(computeFD);
			Map<String, String> huffManCode = huffman_code(huffManTree);
			String output = encode(huffManCode, loadData);
			process_results(computeFD, huffManCode, loadData, output);
		}

	}

	public static String load_data(String inputFile) {
		BufferedReader in = null;
		String line = "";
		try {
			/*
			 * We create a new reader that accepts UTF-8 encoding and extract the input
			 * string from the file, and we return it
			 */
			in = new BufferedReader(new InputStreamReader(new FileInputStream("inputData/" + inputFile), "UTF-8"));
			line = in.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return line;
	}

	/**
	 *
	 * Loops through the input string and if the hashtable already contains that
	 * character in the input, we add 1 to the frequency of said character.
	 * 
	 * Else, it is the first time we see that character so we set the frequency to
	 * one.
	 *
	 * @param input text file string
	 * @return a map with the input characters mapping to their frequencies
	 * 
	 */
	public static Map<String, Integer> compute_fd(String input) {
		Map<String, Integer> map = new HashTableSC<String, Integer>(new SimpleHashFunction<String>());
		for (int i = 0; i < input.length(); i++) {
			String s = String.valueOf(input.charAt(i));
			if (map.containsKey(s)) {
				int freq = map.get(s);
				map.put(s, freq + 1);
			} else {
				map.put(s, 1);
			}
		}
		return map;
	}

	/**
	 *
	 * Creates a key value node taken from the map in compute_fd. Goes through each
	 * key and adds them to a sorted list. Then it loops through the sorted list and
	 * starts building the tree, from leaves to the root
	 *
	 * @param map node containing a key and its frequency
	 * @return the frequencies of the keys
	 * 
	 */
	public static BTNode<Integer, String> huffman_tree(Map<String, Integer> map) {
		SortedList<BTNode<Integer, String>> sL = new SortedArrayList<BTNode<Integer, String>>(map.size());

		for (String k : map.getKeys()) {
			sL.add(new BTNode<Integer, String>(map.get(k), k));
		}
		for (int i = sL.size() - 1; i >= 1; i--) {
			BTNode<Integer, String> l = sL.removeIndex(0);
			BTNode<Integer, String> r = sL.removeIndex(0);
			int nodeFreq = l.getKey() + r.getKey();
			String nodeSymbol = l.getValue() + r.getValue();
			BTNode<Integer, String> dad = new BTNode<Integer, String>(nodeFreq, nodeSymbol);

			dad.setLeftChild(l);
			dad.setRightChild(r);
			sL.add(dad);

		}
		BTNode<Integer, String> test = sL.removeIndex(0);
		// BinaryTreePrinter.print(test);
		return test;

	}

	/**
	 *
	 * Calls the method prefix in order to build the key's code.
	 *
	 * @param huffManTree a node in the huffman tree
	 * @return the frequencies of the keys
	 * 
	 */
	public static Map<String, String> huffman_code(BTNode<Integer, String> huffManTree) {
		Map<String, String> map = new HashTableSC<>(new SimpleHashFunction<>());
		String code = "";
		prefix(huffManTree, map, code);
		return map;

	}

	/**
	 *
	 * First checks if it's a leaf and if it is then just add that key with it's
	 * corresponding code to the map. If it's not a leaf then recursively travel
	 * through the nodes adding 1s and 0s to it's code depending on the path taken
	 * to that key.
	 *
	 * @param N    node in the tree
	 * @param map  node containing a key and its frequency
	 * @param code a string that contains the key's code in 0s and 1s
	 * 
	 */
	public static void prefix(BTNode<Integer, String> N, Map<String, String> map, String code) {
		if (N.getLeftChild() == null && N.getRightChild() == null) {
			map.put(N.getValue(), code);
		} else {
			prefix(N.getLeftChild(), map, code + "0");
			prefix(N.getRightChild(), map, code + "1");
		}

	}

	/**
	 * Converts the input to it's corresponding code
	 *
	 * @param map  a map containing the symbol mapping to it's code
	 * @param data contains the input file words
	 * @return a string that contains the key's code in 0s and 1s
	 */
	public static String encode(Map<String, String> map, String data) {
		String code = "";

		for (int i = 0; i < data.length(); i++) {
			String k = String.valueOf(data.charAt(i));
			code += map.get(k);
		}
		return code;

	}

	/**
	 *
	 * First we loop through the keys on the map containing the keys and their
	 * frequencies and creates a new node with the keys and adds that node to the
	 * sorted list.
	 *
	 * Loop again through the sorted list and then assign each node to a variable.
	 * extract the key, the value from the node and the code from the encoded map.
	 * 
	 *
	 * @param frequencyDistribution map containing a key with its frequency
	 * @param encoded               map containing the key and its code
	 * @param data                  contains the input file words
	 * @param result                data converted into 0s and 1s
	 * 
	 */
	public static void process_results(Map<String, Integer> frequencyDistribution, Map<String, String> encoded,
			String data, String result) {

		SortedArrayList<BTNode<Integer, String>> sL = new SortedArrayList<BTNode<Integer, String>>(
				frequencyDistribution.size());

		// Sort the nodes in the map
		for (String k : frequencyDistribution.getKeys()) {
			BTNode<Integer, String> N = new BTNode<Integer, String>(frequencyDistribution.get(k), k);
			sL.add(N);
		}

		System.out.println("Symbol" + "\t" + "Frequency" + "\t" + "Code");
		System.out.println("------" + "\t" + "---------" + "\t" + "----");

		for (int i = sL.size() - 1; i >= 0; i--) {
			BTNode<Integer, String> N = sL.get(i);
			System.out.println(N.getKey() + "\t" + N.getValue() + "\t\t" + encoded.get(N.getValue()));
		}

		System.out.println("Original String:");
		System.out.println(data);

		System.out.println("Encoded String:");
		System.out.println(result);

		int originalBytes = data.getBytes().length;
		int resultBytes = Math.round((float) result.getBytes().length / 8);
		DecimalFormat d = new DecimalFormat("##.##");
		String space = d.format(100 - (((float) ((float) resultBytes / (float) originalBytes)) * 100));

		System.out.println("The original string requires " + originalBytes + " bytes.");
		System.out.println("The encoded string requires " + resultBytes + " bytes.");
		System.out.println("Difference in space required is " + space + "%.");

	}

}
