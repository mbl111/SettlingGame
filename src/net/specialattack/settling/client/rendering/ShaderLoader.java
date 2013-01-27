
package net.specialattack.settling.client.rendering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderLoader {
    public static int createProgram(String name) {
        int programId = GL20.glCreateProgram();
        int vertexId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        int fragmentId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        String vertexData;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(ShaderLoader.class.getResource("/shaders/" + name + ".vert").toURI())));
            String line = null;

            StringBuilder builder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }

            reader.close();

            vertexData = builder.toString();
        }
        catch (Exception e) {
            return 0;
        }

        String fragmentData;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(ShaderLoader.class.getResource("/shaders/" + name + ".frag").toURI())));
            String line = null;

            StringBuilder builder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }

            reader.close();

            fragmentData = builder.toString();
        }
        catch (Exception e) {
            return 0;
        }

        GL20.glShaderSource(vertexId, vertexData);
        GL20.glCompileShader(vertexId);

        if (GL20.glGetShader(vertexId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Failed compiling vertex shader for " + name);

            return 0;
        }

        GL20.glShaderSource(fragmentId, fragmentData);
        GL20.glCompileShader(fragmentId);

        if (GL20.glGetShader(fragmentId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Failed compiling fragment shader for " + name);

            return 0;
        }

        GL20.glAttachShader(programId, vertexId);
        GL20.glAttachShader(programId, fragmentId);
        GL20.glLinkProgram(programId);

        if (GL20.glGetProgram(programId, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            System.err.println("Failed linking shader for " + name);

            return 0;
        }

        GL20.glDeleteShader(vertexId);
        GL20.glDeleteShader(fragmentId);

        return programId;
    }
}
