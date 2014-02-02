package game.programs;

import android.content.Context;
import game.util.ShaderHelper;
import game.util.TextResourceReader;

import static android.opengl.GLES20.*;

public class ShaderProgram {

    protected static final String U_TIME = "u_Time";

    protected static final String A_DIRECTION_VECTOR = "a_DirectionVector";
    protected static final String A_PARTICLE_START_TIME = "a_ParticleStartTime";


    protected static final String U_MVP_MATRIX = "u_MVPMatrix";
    protected static final String U_MV_MATRIX = "u_MVMatrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_COLOR = "u_Color";
    protected static final String U_LIGHT = "u_LightPosition";

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_NORMAL = "a_Normal";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    public final int program;
    protected ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        program = ShaderHelper.buildProgram(TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId),
                TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId));
    }

    public void useProgram() {
        glUseProgram(program);
    }
}
