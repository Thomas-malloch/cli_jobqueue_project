
package com.thomas.jobqueue;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
public class HuffmanCoding {
	 private Node root;
	 private Map<Character, String> encodingMap;
	 
	 private static class Node implements Comparable<Node> {
	        Character character;
	        int frequency;
	        Node left;
	        Node right;

	        Node(Character character, int frequency) {
	            this.character = character;
	            this.frequency = frequency;
	        }

	        Node(Node left, Node right) {
	            this.frequency = left.frequency + right.frequency;
	            this.left = left;
	            this.right = right;
	        }

	        boolean isLeaf() {
	            return left == null && right == null;
	        }

	        @Override
	        public int compareTo(Node other) {
	            if (this.frequency != other.frequency) {
	                return Integer.compare(this.frequency, other.frequency);
	            }
	            if (this.isLeaf() && other.isLeaf()) {
	                return Character.compare(this.character, other.character);
	            }
	            if (this.isLeaf()) return -1;
	            if (other.isLeaf()) return 1;
	            return 0;
	    }
	}
	 
	public HuffmanCoding(String text) {
		if (text == null || text.isEmpty()) { 
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        buildTree(text); 
        buildEncodingMap();
	}
	
	private void buildTree(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Node> q = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) { 
            q.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (q.size() > 1) { 
            Node left = q.poll(); 
            Node right = q.poll();
            Node parent = new Node(left, right); 
            q.add(parent);
        }

        root = q.poll();
    }
	
	private void buildEncodingMap() {
        encodingMap = new HashMap<>(); 
        buildEncodingMap(root, ""); 
    }

    private void buildEncodingMap(Node node, String code) {
        if (node.isLeaf()) {
            encodingMap.put(node.character, code);
        } else {
            buildEncodingMap(node.left, code + "0"); 
            buildEncodingMap(node.right, code + "1");
        }
    }


	public String encode(String text) {
		StringBuilder encoded = new StringBuilder(); 
        for (char c : text.toCharArray()) { 
            String code = encodingMap.get(c); 
            if (code == null) {
                throw new IllegalArgumentException("Character '" + c + "' not found in encoding map");
            }
            encoded.append(code); 
        }
        return encoded.toString();
		
	}

	public String decode(String encoded) {
		StringBuilder decoded = new StringBuilder();
        Node current = root; 
        
        for (char bit : encoded.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else if (bit == '1') {
                current = current.right;
            } else {
                throw new IllegalArgumentException("Invalid bit in encoded string: " + bit);
            }

            if (current.isLeaf()) { 
                decoded.append(current.character); 
                current = root; 
            }
        }

        if (current != root) {
            throw new IllegalArgumentException("Encoded string is incomplete");
        }

        return decoded.toString();
	}
}
