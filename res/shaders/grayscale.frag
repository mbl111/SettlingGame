uniform sampler2D texture1;

void main() {
    vec4 color = texture2D(texture1, gl_TexCoord[0].st);
    
    float grey = color.r * 0.33 + color.g * 0.33 + color.b * 0.33;
    
    color = vec4(grey, grey, grey, 1);
    
    gl_FragColor = color;
}