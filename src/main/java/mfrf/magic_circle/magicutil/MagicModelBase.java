package mfrf.magic_circle.magicutil;

import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;

import java.util.ArrayList;

public class MagicModelBase extends MagicNodeBase {
    protected MagicNodeBase begin;
    private ArrayList<MagicNodeBase> nodes = null;
    private int edgeCounts = -1;
    private MagicStreamMatrixNByN connectivityMatrix = null;

    public MagicModelBase(MagicNodeBase graph) {
        super(NodeType.MODEL, graph, null, magicStream -> true);
        this.begin = graph;
        this.eigenMatrix = null;
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

    public MagicStreamMatrixNByN getConnectivityMatrix() {
        if (connectivityMatrix == null) {
            ArrayList<MagicNodeBase> nodes = getNodes();
            int nodeCounts = nodes.size();
            connectivityMatrix = new MagicStreamMatrixNByN(nodeCounts, nodeCounts);
            for (int i = 0; i < nodeCounts; i++) {
                connectivityMatrix.set(i, i, 1);
            }
            begin.getConnectivityMatrix(connectivityMatrix, nodes);
        }
        return connectivityMatrix;
    }

    @Override
    public MagicNodePropertyMatrix8By8 getEigenMatrix() {
        if (eigenMatrix == null) {
            MagicNodePropertyMatrix8By8 eigen = MagicNodePropertyMatrix8By8.IDENTITY;
            for (MagicNodeBase node : getNodes()) {
                eigen = eigen.leftTimes(node.eigenMatrix);
            }
            this.eigenMatrix = eigen;
        }
        return super.getEigenMatrix();
    }

    @Override
    public MagicStream apply(MagicStream magic) {
        return null;
    }
}
