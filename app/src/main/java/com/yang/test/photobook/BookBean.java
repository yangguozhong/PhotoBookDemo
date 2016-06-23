package com.yang.test.photobook;

import java.util.List;

/**
 * 项目名称：PhotoBook
 * 类描述：
 * 创建人：yangguozhong
 * 创建时间：2016/6/22 14:59
 * 修改备注：
 */
public class BookBean {
    /**
     * width : 560
     * height : 560
     * band : [{"id":2,"image":{"x":86,"y":86,"width":388,"height":280},"text":{"x":86,"y":416,"width":212,
     * "height":116,"font":14,"alignment":1,"color":"#85614c","line_space":10,"line_number":2},"orientation":1}]
     */

    private int width;
    private int height;
    /**
     * id : 2
     * image : {"x":86,"y":86,"width":388,"height":280}
     * text : {"x":86,"y":416,"width":212,"height":116,"font":14,"alignment":1,"color":"#85614c","line_space":10,
     * "line_number":2}
     * orientation : 1
     */

    private List<BandTemplate> band;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<BandTemplate> getBand() {
        return band;
    }

    public void setBand(List<BandTemplate> band) {
        this.band = band;
    }

    public static class BandTemplate {
        private int id;
        /**
         * x : 86
         * y : 86
         * width : 388
         * height : 280
         */

        private ImageEntity image;
        /**
         * x : 86
         * y : 416
         * width : 212
         * height : 116
         * font : 14
         * alignment : 1
         * color : #85614c
         * line_space : 10
         * line_number : 2
         */

        private TextEntity text;
        private int orientation;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ImageEntity getImage() {
            return image;
        }

        public void setImage(ImageEntity image) {
            this.image = image;
        }

        public TextEntity getText() {
            return text;
        }

        public void setText(TextEntity text) {
            this.text = text;
        }

        public int getOrientation() {
            return orientation;
        }

        public void setOrientation(int orientation) {
            this.orientation = orientation;
        }

        public static class ImageEntity {
            private int x;
            private int y;
            private int width;
            private int height;

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }

        public static class TextEntity {
            private int x;
            private int y;
            private int width;
            private int height;
            private int font;
            private int alignment;
            private String color;
            private int line_space;
            private int line_number;

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getFont() {
                return font;
            }

            public void setFont(int font) {
                this.font = font;
            }

            public int getAlignment() {
                return alignment;
            }

            public void setAlignment(int alignment) {
                this.alignment = alignment;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public int getLine_space() {
                return line_space;
            }

            public void setLine_space(int line_space) {
                this.line_space = line_space;
            }

            public int getLine_number() {
                return line_number;
            }

            public void setLine_number(int line_number) {
                this.line_number = line_number;
            }
        }
    }
}
