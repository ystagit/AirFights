uniform mat4 u_MVPMatrix;
uniform mat4 u_MVMatrix;
                        
attribute vec4 a_Position;
attribute vec4 a_Color;
attribute vec3 a_Normal;
attribute vec2 a_TextureCoordinates;

varying vec3 v_Position;
varying vec4 v_Color;
varying vec3 v_Normal;
varying vec2 v_TextureCoordinates;
                  
void main() {

    v_Position = vec3(u_MVMatrix * a_Position);
    v_Color = a_Color;
    v_TextureCoordinates = a_TextureCoordinates;
    v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));

    gl_Position = u_MVPMatrix * a_Position;

}