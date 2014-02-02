package game.programs;

import android.content.Context;
import static main.java.game.R.raw.*;

import static android.opengl.GLES20.*;

public class Object3DShaderProgram extends ShaderProgram {

    public final int uMVPMatrixLocation;
    public final int uMVMatrixLocation;
    private final int uTextureUnitLocation;
    public final int uLightLocation;

    private final int aPositionLocation;
    private final int aNormalLocation;
    private final int aTextureCoordinatesLocation;

    public Object3DShaderProgram(Context context) {
        //load file with .GLSL type
        super(context, object_vertex_shader, object_fragment_shader);

        uMVPMatrixLocation = glGetUniformLocation(program, U_MVP_MATRIX);
        uMVMatrixLocation = glGetUniformLocation(program, U_MV_MATRIX);
        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);
        uLightLocation = glGetUniformLocation(program, U_LIGHT);

        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aNormalLocation = glGetAttribLocation(program, A_NORMAL);
        aTextureCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    public void setUniforms(float[] matrix, float[] lightPosInEyeSpace, int textureId) {
        glUniformMatrix4fv(uMVMatrixLocation, 1, false, matrix, 0);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, matrix, 0);
        glUniform3f(uLightLocation, lightPosInEyeSpace[0], lightPosInEyeSpace[1], lightPosInEyeSpace[2]);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glUniform1i(uTextureUnitLocation, 0);
    }

    public int getPositionAttrLoc() {
        return aPositionLocation;
    }

    public int getNormalAttrLoc() {
        return aNormalLocation;
    }

    public int getTextureCoordinateAttrLoc() {
        return aTextureCoordinatesLocation;
    }

}
