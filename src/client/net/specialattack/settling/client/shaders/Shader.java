
package net.specialattack.settling.client.shaders;

import org.lwjgl.opengl.GL20;

public class Shader {

    public int programId;

    public Shader(int programId) {
        this.programId = programId;
    }

    public void bindShader() {
        GL20.glUseProgram(this.programId);
    }

    public static void unbindShader() {
        GL20.glUseProgram(0);
    }

}
