package bintree;

import com.sun.javafx.font.FontConstants;

public class Main {

    public static void main(String[] args) {
       BinTree<Integer, String> tree = new BinTree<>();
       BinTree.Visitor<Integer, String> visitor = new BinTree.Visitor<Integer, String>() {
           @Override
           public void visit(Integer key, String value) {
               System.out.println(key + ": " + value);
           }
       };
       
       tree.put(10, "");
       tree.put(7, "");
       tree.put(15, "");
       tree.put(3, "");
       tree.put(8, "");
       tree.put(13, "");
       tree.put(16, "");
       tree.put(1, "");
       tree.put(4, "");
       tree.put(14, "");
       tree.put(12, "");
       
       //tree.depthFirstSearch(visitor);
       tree.widthFisrtSearch(visitor);
    }
    
}
