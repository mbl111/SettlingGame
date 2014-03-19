
package net.specialattack.settling.client.shaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

import net.specialattack.settling.common.Settling;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 * Class for loading GL Shaders
 * 
 * @author heldplayer
 * 
 */
public class ShaderLoader {

    public static Shader createShader(String name) {
        BufferedReader vertex;
        try {
            vertex = new BufferedReader(new FileReader(new File(ShaderLoader.class.getResource("/shaders/" + name + ".vert").toURI())));
        }
        catch (Exception e) {
            return null;
        }
        BufferedReader fragment;
        try {
            fragment = new BufferedReader(new FileReader(new File(ShaderLoader.class.getResource("/shaders/" + name + ".vert").toURI())));
        }
        catch (Exception e) {
            try {
                vertex.close();
            }
            catch (IOException e2) {}
            return null;
        }
        return createShader(name, vertex, fragment);
    }

    /**
     * Creates a shader from given parameters
     * 
     * @param name
     *        Name of the shader, purely used for writing error messages
     * @param vertex
     *        The vertex shader part
     * @param fragment
     *        The fragment shader part
     * @return A shader object representing the newly created shader
     */
    public static Shader createShader(String name, BufferedReader vertex, BufferedReader fragment) {
        int programId = GL20.glCreateProgram();
        int vertexId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        int fragmentId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        String vertexData = null;

        try {
            String line = null;

            StringBuilder builder = new StringBuilder();

            while ((line = vertex.readLine()) != null) {
                builder.append(line).append('\n');
            }

            vertex.close();

            vertexData = builder.toString();
        }
        catch (Exception e) {
            Settling.log.log(Level.WARNING, "Failed finding vertex shader part for " + name);
        }

        String fragmentData = null;

        try {
            String line = null;

            StringBuilder builder = new StringBuilder();

            while ((line = fragment.readLine()) != null) {
                builder.append(line).append('\n');
            }

            fragment.close();

            fragmentData = builder.toString();
        }
        catch (Exception e) {
            Settling.log.log(Level.WARNING, "Failed finding fragment shader part for " + name);
        }

        if (vertexData != null) {
            GL20.glShaderSource(vertexId, vertexData);
            GL20.glCompileShader(vertexId);

            if (GL20.glGetShaderi(vertexId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
                Settling.log.log(Level.WARNING, "Failed compiling vertex shader for " + name);
                Settling.log.log(Level.WARNING, GL20.glGetShaderInfoLog(vertexId, 1024));

                GL20.glDeleteProgram(programId);
                GL20.glDeleteShader(vertexId);
                GL20.glDeleteShader(fragmentId);

                return null;
            }
        }

        if (fragmentData != null) {
            GL20.glShaderSource(fragmentId, fragmentData);
            GL20.glCompileShader(fragmentId);

            if (GL20.glGetShaderi(fragmentId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
                Settling.log.log(Level.WARNING, "Failed compiling fragment shader for " + name);
                Settling.log.log(Level.WARNING, GL20.glGetShaderInfoLog(fragmentId, 1024));

                GL20.glDeleteProgram(programId);
                GL20.glDeleteShader(vertexId);
                GL20.glDeleteShader(fragmentId);

                return null;
            }
        }

        if (vertexData == null || fragmentData == null) {
            Settling.log.log(Level.WARNING, "Shader did not load for both vertex and fragment for " + name);

            GL20.glDeleteProgram(programId);
            GL20.glDeleteShader(vertexId);
            GL20.glDeleteShader(fragmentId);

            return null;
        }

        GL20.glAttachShader(programId, vertexId);
        GL20.glAttachShader(programId, fragmentId);
        GL20.glLinkProgram(programId);

        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            Settling.log.log(Level.WARNING, "Failed linking shader for " + name);
            Settling.log.log(Level.WARNING, GL20.glGetProgramInfoLog(programId, 1024));

            GL20.glDetachShader(programId, vertexId);
            GL20.glDetachShader(programId, fragmentId);
            GL20.glDeleteProgram(programId);
            GL20.glDeleteShader(vertexId);
            GL20.glDeleteShader(fragmentId);

            return null;
        }

        return new Shader(vertexId, fragmentId, programId);
    }

}
