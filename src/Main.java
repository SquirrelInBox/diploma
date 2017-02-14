import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class Main
{
    static int LEVEL_COUNT = 1;
    public static void main(String[] args) throws IOException
    {
        Map<Integer, List<Tree>> trees = generateTrees(10);
        trees.keySet().forEach(i ->System.out.println(String.format("%s: %s", i, trees.get(i).size())));
        saveTrees(trees);
    }

    private static Map<Integer, List<Tree>> generateTrees(int listCount)
    {
        Map<Integer, List<Tree>> trees = new HashMap<>();

        Tree previousRoot = new Tree(new Node(null, null, null), true);
        trees.put(1, Collections.singletonList(previousRoot));

        Node parent = new Node(null, null, null);
        Node son = new Node(parent, null, null);
        Node daughter = new Node(parent, null, null);
        parent.setSon(son);
        parent.setDaughter(daughter);

        Tree doubleTree = new Tree(parent, true);

        trees.put(2, Collections.singletonList(doubleTree));

        int leafs = 3;

        for (int k = 2; k < listCount; k++)
        {
            List<Tree> resultTrees = new ArrayList<>();
            int size = trees.size();
            System.out.println(String.format("%s:", k+1));
            for (int i = 0; i < size / 2 + 1; i++)
            {
                for (Tree leftTree : trees.get(i + 1))
                {
                    for (Tree rightTree : trees.get(size - i))
                    {
                        Tree newLeftTree = createNewTree(leftTree, rightTree);

                        if (newLeftTree.isRespectful() && !isDouble(newLeftTree, resultTrees))
                        {
                            resultTrees.add(newLeftTree);
//                            System.out.println(String.format("  %s + %s", i + 1, size - i));
                        }

                        Tree newRightTree = createNewTree(rightTree, leftTree);
                        if (newRightTree.isRespectful() && !isDouble(newRightTree, resultTrees))
                        {
                            resultTrees.add(newRightTree);
//                            System.out.println(String.format("  %s + %s", size - i, i + 1));
                        }

                    }
                }
            }
            trees.put(leafs++, resultTrees);
        }

        return trees;
    }

    private static Tree createNewTree(Tree leftTree, Tree rightTree)
    {
        Tree copyLeftTree = leftTree.copy();
        Tree copyRightTree = rightTree.copy();
        Node newNode = new Node(null, copyLeftTree.getRoot(), copyRightTree.getRoot());
        copyLeftTree.getRoot().setParent(newNode);
        copyRightTree.getRoot().setParent(newNode);
        copyLeftTree.setRoot(newNode);
        copyRightTree.setRoot(newNode);

        Tree newTree = new Tree();
        newTree.setRoot(newNode);
        Tree.setRespectfulInTree(newTree);

        return newTree;
    }

    private static boolean isDouble(Tree tree, List<Tree> trees)
    {
        for (Tree tempTree : trees)
        {
            if (tempTree.equivalentTrees(tree))
            {
                return true;
            }
        }

        return false;
    }

    public static void saveTrees(Map<Integer, List<Tree>> trees) throws IOException
    {
        for (int i : trees.keySet())
        {
            OutputStream os = new FileOutputStream(String.format("in%s.tex", i));
            writeTitle(os);
            LEVEL_COUNT = (int) StrictMath.round(Math.log(i)/Math.log(2));
            for (Tree tree : trees.get(i))
            {
                writeSubTitle(os);
                String treeLine = "\\" + Node.writeNode(tree.getRoot()) + ";";
                os.write(treeLine.getBytes(), 0, treeLine.length());
                writeSubFinish(os);
            }
            writeFinish(os);
        }
    }

    public static void writeTitle(OutputStream os) throws IOException
    {
        String title =
                "\\documentclass{article}\n" +
                "\\usepackage{tikz}\n" +
                "\\begin{document}\n";
        os.write(title.getBytes(), 0, title.length());
    }

    public static void writeSubTitle(OutputStream os) throws IOException
    {
        String title = "\\begin{tikzpicture}\n\\tikzstyle{solid node}=[circle,draw,inner sep=1.5,fill=black]\n";
        double distance = 0.3;
        for (int i = LEVEL_COUNT; i >= 1; i--)
        {
            title += "\\tikzstyle{level "+i+"}=[level distance=5mm,sibling distance="+distance+"cm]\n";
            distance *= 2;
        }
        os.write(title.getBytes(), 0, title.length());
    }

    public static void writeSubFinish(OutputStream os) throws IOException
    {
        String end = "\n\\end{tikzpicture}\n";
        os.write(end.getBytes(), 0, end.length());
    }

    public static void writeFinish(OutputStream os) throws IOException
    {
        String end = "\\end{document}\n";
        os.write(end.getBytes(), 0, end.length());
    }
}
