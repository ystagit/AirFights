package game.data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public class VertexArray {
    private final FloatBuffer floatBuffer;
    private final ByteBuffer byteBuffer;
    private static final int BYTES_PER_FLOAT = 4;

    public VertexArray(float[] vertexData) {
        byteBuffer = ByteBuffer.allocateDirect(vertexData.length * BYTES_PER_FLOAT);
        byteBuffer.order(ByteOrder.nativeOrder());
        floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(vertexData);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation, int componentCount, int stride) {
        floatBuffer.position(dataOffset);
        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT, false, stride, floatBuffer);
        glEnableVertexAttribArray(attributeLocation);

        floatBuffer.position(0);
    }

    public void updateBuffer(float[] vertexData, int start, int count) {
        floatBuffer.position(start);
        floatBuffer.put(vertexData, start, count);
        floatBuffer.position(0);
    }

}
