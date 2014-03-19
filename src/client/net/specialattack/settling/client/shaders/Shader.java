
package net.specialattack.settling.client.shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {

    /**
     * The program ID of the shader
     */
    public final int programId;

    public Shader(int programId) {
        this.programId = programId;
    }

    /**
     * Binds the shader
     */
    public void bindShader() {
        Shader.prevShader = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
        GL20.glUseProgram(this.programId);
    }

    /**
     * Integer used to store the current active shader in case of other active
     * shaders
     */
    public static int prevShader = 0;

    /**
     * Unbinds the current shader and returns to the previous shader
     */
    public static void unbindShader() {
        GL20.glUseProgram(Shader.prevShader);
        Shader.prevShader = 0;
    }

}
