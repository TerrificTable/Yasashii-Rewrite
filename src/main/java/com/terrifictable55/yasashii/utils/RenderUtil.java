package com.terrifictable55.yasashii.utils;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil {
    public static void drawSexyRect(double posX, double posY, double posX2, double posY2, int col1, int col2) {
        drawRect(posX, posY, posX2, posY2, col2);
        float alpha = (col1 >> 24 & 0xFF) / 255F;
        float red = (col1 >> 16 & 0xFF) / 255F;
        float green = (col1 >> 8 & 0xFF) / 255F;
        float blue = (col1 & 0xFF) / 255F;
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glPushMatrix();
        glColor4f(red, green, blue, alpha);
        glLineWidth(3);
        glBegin(GL_LINES);
        glVertex2d(posX, posY);
        glVertex2d(posX, posY2);
        glVertex2d(posX2, posY2);
        glVertex2d(posX2, posY);
        glVertex2d(posX, posY);
        glVertex2d(posX2, posY);
        glVertex2d(posX, posY2);
        glVertex2d(posX2, posY2);
        glEnd();
        glPopMatrix();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
    }

    public static void drawRect(double par0, double par1, double par2, double par3, int par4) {
        double var5;
        if (par0 < par2) {
            var5 = par0;
            par0 = par2;
            par2 = var5;
        }
        if (par1 < par3) {
            var5 = par1;
            par1 = par3;
            par3 = var5;
        }
        float var10 = (par4 >> 24 & 255) / 255.0F;
        float var6 = (par4 >> 16 & 255) / 255.0F;
        float var7 = (par4 >> 8 & 255) / 255.0F;
        float var8 = (par4 & 255) / 255.0F;
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        glColor4f(var6, var7, var8, var10);
        glBegin(GL_QUADS);
        glVertex3d(par0, par3, 0.0D);
        glVertex3d(par2, par3, 0.0D);
        glVertex3d(par2, par1, 0.0D);
        glVertex3d(par0, par1, 0.0D);
        glEnd();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
    }

    public static void drawBoundingBox(AxisAlignedBB bb, float width, float red, float green, float blue, float alpha) {
        GlStateManager.pushMatrix();
        HackTessellator tessellator = HackTessellator.INSTANCE;
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, 0.0F).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, 0.0F).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, 0.0F).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, 0.0F).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, 0.0F).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
    }
}
