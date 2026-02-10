package com.xiao.Frame;

import com.xiao.Object.BoardObject;
import com.xiao.Object.StoneObject;
import com.xiao.Util.GameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {
    GameFrame frame;
    // 双缓存绘图
    private Image offScreenImage;

    // 参数
    int width = 1200;
    int height = 700;

    // 棋子
    public List<StoneObject> stones = new ArrayList<>();

    // 棋盘
    BoardObject boardObject = new BoardObject(this);

    // 提示词
    private String tip = "";

    private boolean gameOver = false;

    /**
     * 创建游戏窗口
     */
    public void launchGameFrame() {
        this.setVisible(true);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setTitle("五子棋");

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (gameOver) {
                    return;
                }

                int ex = e.getX(), ey = e.getY();
                int[] cross = boardObject.snap(ex, ey);
                int gx = cross[0], gy = cross[1];

                if (!boardObject.inBounds(gx, gy)) {
                    tip = "请在棋盘内落子";
                    repaint();
                    return;
                }

                tip = "";

                // 黑白交替
                Color c = stones.size() % 2 == 0 ? Color.black : Color.white;
                StoneObject stone = new StoneObject(gx,gy,15,c);
                stones.add(stone);
                if (checkWin(boardObject,gx, gy)){
                    tip = stones.size() % 2 == 0 ? "白子获胜" : "黑子获胜";
                    gameOver = true;
                }
                repaint();
            }
        });
    }

    public void paint(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(width, height);
        }
        Graphics graphics = offScreenImage.getGraphics();

        // 背景
        graphics.setColor(Color.gray);
        graphics.fillRect(0, 0, width, height);

        // 棋盘
        boardObject.paintSelf(graphics);

        // 画所有已落子
        for (StoneObject s : stones) {
            graphics.setColor(s.color);
            graphics.fillOval(s.x - s.radius, s.y - s.radius, 2*s.radius, 2*s.radius);
        }

        // 提示语
        if (!tip.isEmpty()) {
            GameUtils.drawWord(graphics, tip, Color.black, 24, 700, 450);
        }

        g.drawImage(offScreenImage, 0, 0, null);
    }

    // 落子并判断胜利
    public boolean checkWin(BoardObject boardObject, int gx, int gy) {
        // 将像素坐标转换为棋盘数组索引
        int i = (gx - boardObject.x) / boardObject.interval;
        int j = (gy - boardObject.y) / boardObject.interval;

        // 设置当前棋子颜色（1:黑, 2:白）
        int currentColor = stones.size() % 2 == 1 ? 1 : 2;  // 注意：这里stones.size()是落子前的数量
        boardObject.board[i][j] = currentColor;

        // 八个方向的向量
        int[][] directions = {
                {1, 0},   // 水平向右
                {0, 1},   // 垂直向下
                {1, 1},   // 右下对角线
                {1, -1}   // 右上对角线
        };

        // 检查每个方向
        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];
            int count = 1;  // 当前位置已经有1个棋子

            // 正向检查
            for (int step = 1; step <= 4; step++) {
                int ni = i + dx * step;
                int nj = j + dy * step;

                if (ni < 0 || ni >= boardObject.size || nj < 0 || nj >= boardObject.size || boardObject.board[ni][nj] != currentColor) {
                    break;
                }
                count++;
            }

            // 反向检查
            for (int step = 1; step <= 4; step++) {
                int ni = i - dx * step;
                int nj = j - dy * step;

                if (ni < 0 || ni >= boardObject.size || nj < 0 || nj >= boardObject.size || boardObject.board[ni][nj] != currentColor) {
                    break;
                }
                count++;
            }

            // 如果连续5个或以上相同颜色的棋子
            if (count >= 5) {
                return true;
            }
        }

        return false;
    }
}