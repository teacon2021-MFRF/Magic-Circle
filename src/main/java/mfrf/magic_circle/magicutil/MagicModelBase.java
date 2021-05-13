package mfrf.magic_circle.magicutil;

import mfrf.magic_circle.magicutil.datastructure.DecimalMagicMatrixNByN;

import java.util.ArrayList;

public class MagicModelBase {
    protected Receiver receiver = null;
    protected Invoker invoker = null;
    protected MagicNodeBase begin;
    private ArrayList<MagicNodeBase> nodes = null;
    private int edgeCounts = -1;
    private DecimalMagicMatrixNByN connectivityMatrix = null;

    public MagicModelBase(Receiver receiver, Invoker invoker, MagicNodeBase graph) {
        this.receiver = receiver;
        this.invoker = invoker;
        this.begin = graph;
    }

    public int getEdges() {
        if (edgeCounts == -1) {
            edgeCounts = (int) getConnectivityMatrix().sumAll();
        }
        return edgeCounts;
    }

    public ArrayList<MagicNodeBase> getNodes() {
        if (nodes == null)
            nodes = begin.getNodes(new ArrayList<>());
        return nodes;
    }

    public DecimalMagicMatrixNByN getConnectivityMatrix() {
        if (connectivityMatrix == null) {
            ArrayList<MagicNodeBase> nodes = getNodes();
            int nodeCounts = nodes.size();
            connectivityMatrix = new DecimalMagicMatrixNByN(nodeCounts, nodeCounts);
            for (int i = 0; i < nodeCounts; i++) {
                connectivityMatrix.set(i, i, 1);
            }
            begin.getConnectivityMatrix(connectivityMatrix, nodes);
        }
        return connectivityMatrix;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public Invoker getInvoker() {
        return invoker;
    }
}
