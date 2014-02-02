precision mediump float;

uniform vec3 u_LightPosition;
uniform sampler2D u_TextureUnit;

varying vec3 v_Position;
varying vec4 v_Color;

varying vec3 v_Normal;
varying vec2 v_TextureCoordinates;

void main() {

    float distance = length(u_LightPosition - v_Position);
    vec3 lightVector = normalize(u_LightPosition - v_Position);
    float diffuse = max(dot(v_Normal, lightVector), 0.0);
    diffuse = diffuse * (1.0 / (1.0 + (0.10 * distance)));
    diffuse = diffuse + 0.3;

    gl_FragColor = diffuse * texture2D(u_TextureUnit, v_TextureCoordinates);
}