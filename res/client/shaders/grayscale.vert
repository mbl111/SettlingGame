varying vec4 vertColor;

void main() {
    gl_Position = ftransform(); //Transform the vertex position
    gl_TexCoord[0] = gl_MultiTexCoord0;
    
    vertColor = gl_Color;
}