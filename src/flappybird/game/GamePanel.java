package flappybird.game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import java.sql.SQLException;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import flappybird.ui.AccountPanel;
import flappybird.ui.GameOverPanel;
import flappybird.ui.LoginPanel;
import flappybird.ui.MenuPanel;
import flappybird.ui.PausePanel;
import flappybird.ui.RankingPanel;
import flappybird.ui.RegisterPanel;
import flappybird.ui.SkinPanel;

public class GamePanel extends JPanel implements KeyListener, MouseListener{
    private Game game;
    private Timer timer;

    private JScrollPane rankingJScrollPane;
    
    private MenuPanel menuPanel;
    private GameOverPanel gameOverPanel;
    private PausePanel pausePanel;
    private RankingPanel rankingPanel;
    private SkinPanel skinPanel;

    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private AccountPanel accountPanel;
    private GameState previousState;

    public GamePanel() {

        game = new Game();
        previousState = game.getState();

        skinPanel = new SkinPanel(game.getSkinManager());
        menuPanel = new MenuPanel();
        gameOverPanel = new GameOverPanel();
        pausePanel = new PausePanel();
        rankingPanel = new RankingPanel();

        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();
        accountPanel = new AccountPanel();

        //Dùng null layout để tự đặt vị trí JScrollPane.
        // Vì GamePanel đang tự vẽ giao diện bằng Graphics.
        setLayout(null);

        add(loginPanel);
        add(registerPanel);
        add(accountPanel);

        loginPanel.setVisible(false);
        registerPanel.setVisible(false);
        accountPanel.setVisible(false);

        setupAccountPanelActions();

        updateAuthPanelBounds();


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateAuthPanelBounds();
            }
        });

        loginPanel.getRegisterButton().addActionListener(e -> {
        game.setState(GameState.REGISTER);

        loginPanel.setVisible(false);
        registerPanel.setVisible(true);

        repaint();
        });

        loginPanel.getBackButton().addActionListener(e -> {
            game.setState(GameState.MENU);

            loginPanel.setVisible(false);

            repaint();
            requestFocusInWindow();
        });

        loginPanel.getLoginButton().addActionListener(e ->{
            String username = loginPanel.getUsername();
            String password = loginPanel.getPassword();

            boolean success = game.login(username, password);

            if(success) {
                System.out.println("Đăng nhập thành công!");

                game.setState(GameState.MENU);

                loginPanel.setVisible(false);

                repaint();

                requestFocusInWindow();
            }
            else{
                System.out.println("Sai username hoặc password!");
                loginPanel.setErrorMessage("Sai tên đăng nhập hoặc mật khẩu.");
            }
        });

        registerPanel.getLoginButton().addActionListener(e -> {
            game.setState(GameState.LOGIN);

            registerPanel.setVisible(false);
            loginPanel.setVisible(true);

            repaint();
        });

        registerPanel.getRegisterButton().addActionListener(e ->{
            String username = registerPanel.getUsername();
            String password = registerPanel.getPassword();
            String comfirmPassword = registerPanel.getConfirmPassword();

            if(username.isBlank() || password.isBlank() || comfirmPassword.isBlank()){
                System.out.println("Vui lòng nhập đầy đủ thông tin");
                return;
            }

            if(!password.equals(comfirmPassword)){
                System.out.println("Mật khẩu xác nhận không khớp");
                return;
            }

            boolean success = game.register(username, password);

            if(success) {
                System.out.println("Đăng ký thành công!");

                game.setState(GameState.LOGIN);
                registerPanel.setVisible(false);
                loginPanel.setVisible(true);

                repaint();
                requestFocusInWindow();
            }else{
                System.out.println("Đăng ký thất bại: tài khoản đã tồn tại hoặc có lỗi database");
                registerPanel.setErrorMessage("Tài khoản đã tồn tại hoặc có lỗi hệ thống.");
            }
        });

        registerPanel.getBackButton().addActionListener(e -> {
            game.setState(GameState.MENU);

            registerPanel.setVisible(false);

            repaint();
            requestFocusInWindow();
        });


        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        // Bắt phím ESC kể cả khi đang nhập tài khoản hoặc mật khẩu
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEscape();
            }
        });

        addMouseWheelListener(e -> {
            if (game.getState() == GameState.SKINS) {
                skinPanel.scroll(e.getWheelRotation() * 30);
                repaint();
            }
        });

        //Không tạo nó trong paintComponent().
        rankingJScrollPane = rankingPanel.createRankingScrollPane();
        //Add vào GamePanel nhưng ẩn lúc đầu.
        add(rankingJScrollPane);
        rankingJScrollPane.setVisible(false);

        timer = new Timer(16, e ->{ //Timer(khoảng thời gian,việc cần thực hiện);  -> là cú pháp của lambda expression nhận e thực hiện lệnh bên phải
            game.update();
            // Chỉ tải Top 3 một lần khi vừa chuyển sang GAME_OVER.
            if (game.getState() == GameState.GAME_OVER
                    && previousState != GameState.GAME_OVER) {
                gameOverPanel.refreshTopScores();
            }
            previousState = game.getState();
            repaint();
        });
        //1 giây / 0.016 giây ≈ 62.5 lần
        timer.start();
        
        requestFocusInWindow();
    }

    private void updateAuthPanelBounds() {
        int panelWidth = 320;
        int panelHeight = 380;

        int x = (getWidth() - panelWidth) / 2;
        int y = (getHeight() - panelHeight) / 2 - 35;

        loginPanel.setBounds(x, y, panelWidth, panelHeight);
        registerPanel.setBounds(x, y, panelWidth, panelHeight);

        int accountX = (getWidth() - AccountPanel.PANEL_WIDTH) / 2;
        int accountY = (getHeight() - AccountPanel.PANEL_HEIGHT) / 2;
        accountPanel.setBounds(accountX, accountY, AccountPanel.PANEL_WIDTH, AccountPanel.PANEL_HEIGHT);
    }

    // ================= ACCOUNT =================

    // Gắn hành động cho các nút trong AccountPanel.
    // Luồng chung: AccountPanel (lấy input) -> Game -> Service -> DAO -> MySQL
    private void setupAccountPanelActions() {

        // DELETE 1 lượt chơi
        accountPanel.getDeleteScoreButton().addActionListener(e -> {
            int scoreId = accountPanel.getSelectedScoreId();
            if (scoreId == -1) {
                accountPanel.setMessage("Hãy chọn 1 lượt chơi trong bảng trước.", true);
                return;
            }

            int choice = JOptionPane.showConfirmDialog(this,
                    "Xóa lượt chơi này? Không thể hoàn tác.",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                if (game.deleteMyScore(scoreId)) {
                    refreshAccountPanel();
                    accountPanel.setMessage("Đã xóa lượt chơi.", false);
                } else {
                    accountPanel.setMessage("Không xóa được lượt chơi này.", true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                accountPanel.setMessage("Lỗi database khi xóa lượt chơi.", true);
            }
        });

        // UPDATE mật khẩu
        accountPanel.getChangePasswordButton().addActionListener(e -> {
            String error = accountPanel.getChangePasswordValidationMessage();
            if (!error.isEmpty()) {
                accountPanel.setMessage(error, true);
                return;
            }

            try {
                if (game.changePassword(accountPanel.getOldPassword(), accountPanel.getNewPassword())) {
                    accountPanel.clearPasswordFields();
                    accountPanel.setMessage("Đổi mật khẩu thành công!", false);
                } else {
                    accountPanel.setMessage("Mật khẩu hiện tại không đúng.", true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                accountPanel.setMessage("Lỗi database khi đổi mật khẩu.", true);
            }
        });

        // DELETE tài khoản
        accountPanel.getDeleteAccountButton().addActionListener(e -> {
            JPasswordField passwordField = new JPasswordField();
            Object[] message = {
                "Toàn bộ lịch sử chơi sẽ bị xóa vĩnh viễn.",
                "Nhập mật khẩu để xác nhận:",
                passwordField
            };

            int choice = JOptionPane.showConfirmDialog(this, message,
                    "Xóa tài khoản", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice != JOptionPane.OK_OPTION) {
                return;
            }

            try {
                if (game.deleteAccount(new String(passwordField.getPassword()))) {
                    hideAccountPanel(); // game.deleteAccount đã tự logout và về MENU
                    JOptionPane.showMessageDialog(this, "Đã xóa tài khoản.");
                } else {
                    accountPanel.setMessage("Mật khẩu không đúng, chưa xóa tài khoản.", true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                accountPanel.setMessage("Lỗi database khi xóa tài khoản.", true);
            }
        });

        // BACK
        accountPanel.getBackButton().addActionListener(e -> {
            game.playClickSound();
            hideAccountPanel();
            game.setState(GameState.MENU);
        });
    }

    // READ: tải lại thông tin + lịch sử chơi từ database
    private void refreshAccountPanel() {
        try {
            accountPanel.loadData(game.getCurrentUser(), game.getMyScores());
        } catch (SQLException ex) {
            ex.printStackTrace();
            accountPanel.showLoadError();
            accountPanel.setMessage("Không tải được dữ liệu từ database.", true);
        }
    }

    private void showAccountPanel() {
        game.setState(GameState.ACCOUNT);
        accountPanel.clearPasswordFields();
        accountPanel.clearMessage();
        refreshAccountPanel();
        accountPanel.setVisible(true);
        repaint();
    }

    private void hideAccountPanel() {
        accountPanel.setVisible(false);
        repaint();
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Background luôn được vẽ
        game.getBackground1().draw(g);
        game.getBackground2().draw(g);

        // Kiểm tra trạng thái
        if(game.getState() == GameState.MENU){//=> tức là Game mới là thằng quản lý trạng thái, còn GamePanel chỉ nhìn trạng thái đó để quyết định vẽ cái gì.
            menuPanel.drawMenu(g, getWidth(), getHeight(), game.isLoggedIn());

            game.getGround1().draw(g);
            game.getGround2().draw(g);
        }
        else if(game.getState() == GameState.GAME_OVER){
            game.getGround1().draw(g);
            game.getGround2().draw(g);
            gameOverPanel.drawGameOver(g, getWidth(), getHeight(), game.getScore());
        }
        else if(game.getState() == GameState.PAUSE){
            game.getGround1().draw(g);
            game.getGround2().draw(g);
            pausePanel.drawPause(g, getWidth(), getHeight(), game.getScore());
        }
        else if (game.getState() == GameState.RANKING){
            game.getGround1().draw(g);
            game.getGround2().draw(g);
            rankingPanel.drawRanking(g, getWidth(), getHeight());
            rankingPanel.drawBackButton(g,getWidth(),getHeight());

            updateRankingScrollPaneBounds();
        }
        else if (game.getState() == GameState.SKINS) {
            skinPanel.setCurrentScore(game.getScore());
            skinPanel.drawSkinPanel((Graphics2D) g, getWidth(), getHeight());
        }
        else if (
                game.getState() == GameState.LOGIN
                || game.getState() == GameState.REGISTER
                || game.getState() == GameState.ACCOUNT
        ) {

            // Không vẽ game ở đây.
            // LoginPanel / RegisterPanel / AccountPanel sẽ tự hiển thị
            // vì chúng là JPanel con của GamePanel.
        }
        else{
            drawGame(g);
            game.getGround1().draw(g);
            game.getGround2().draw(g);
        }
    }

    private void drawGame(Graphics g){
        game.drawPipes(g);

        game.getBird().draw(g);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + game.getScore(), 20, 30);
    }

    // Đặt vị trí và kích thước JScrollPane.
    private void updateRankingScrollPaneBounds() {

        if (rankingJScrollPane == null) {
            return;
        }
        int menuWidth = rankingPanel.getMenuWidth();
        int menuHeight = rankingPanel.getMenuHeight();

        int menuX = (getWidth() - menuWidth) / 2;
        int menuY = (getHeight() - menuHeight) / 2;

        
        //JScrollPane nằm ở giữa khung Ranking.
        // menuY + 70:
        //    bắt đầu bên dưới title.

        // 500 x 280:
        //  vùng hiển thị danh sách.
        rankingJScrollPane.setBounds(menuX + 50, menuY + 70, 500, 280);
    }

    /*
     * Hiện JScrollPane khi vào Ranking.
     */
    private void showRankingScrollPane() {
        rankingPanel.refreshRanking();

        rankingJScrollPane.setVisible(true);

        updateRankingScrollPaneBounds();

        revalidate();
        repaint();
    }

    /*
     * Ẩn JScrollPane khi rời Ranking.
     */
    private void hideRankingScrollPane() {

        rankingJScrollPane.setVisible(false);

        revalidate();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE
                || e.getKeyCode() == KeyEvent.VK_UP) {
            game.jump();
        }
    }

    private void handleEscape() {
        GameState state = game.getState();

        switch (state) {
            case PLAYING:
                game.setState(GameState.PAUSE);
                break;

            case PAUSE:
                game.setState(GameState.READY);
                break;

            case SKINS:
                game.setState(GameState.MENU);
                break;

            case RANKING:
                hideRankingScrollPane();
                game.setState(GameState.MENU);
                break;

            case LOGIN:
                loginPanel.setVisible(false);
                game.setState(GameState.MENU);
                requestFocusInWindow();
                break;

            case REGISTER:
                registerPanel.setVisible(false);
                game.setState(GameState.MENU);
                requestFocusInWindow();
                break;

            case ACCOUNT:
                hideAccountPanel();
                game.setState(GameState.MENU);
                break;

            default:
                // MENU và GAME_OVER: không làm gì
                break;
        }

        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }
        //MENU
        if(game.getState() == GameState.MENU){
            String button = menuPanel.getClickedButton( e.getX(), e.getY(), getWidth(), getHeight(), game.isLoggedIn());

            if (button == null){
                return;
            }

            game.playClickSound();

            // ==       → hai biến có trỏ cùng object không?
            //.equals  → hai object có cùng nội dung không?
            if (button.equals("PLAY")){ //equals() so sánh nội dung.// còn == so sánh 2 chuỗi luôn luôn là false vì ở 2 ô nhớ khác nhau là 2 object khác nhau
                game.resetGame();
                System.out.println(game.getState()); // in console để sau này mò lỗi cho dễ
            }
            else if (button.equals("SKINS")) {
                game.setState(GameState.SKINS);
                System.out.println(game.getState());
            }
            else if (button.equals("RANKING")) {
                game.setState(GameState.RANKING);
                showRankingScrollPane();
                System.out.println(game.getState());
            }
            else if (button.equals("LOGIN")) {
                game.setState(GameState.LOGIN);
                loginPanel.setVisible(true);
                registerPanel.setVisible(false);
                loginPanel.requestFocusInWindow();
                repaint();
                System.out.println(game.getState());
            }
            else if (button.equals("ACCOUNT")) {
                showAccountPanel();
                System.out.println(game.getState());
            }
            else if (button.equals("LOGOUT")) {
                game.logout();
                loginPanel.setVisible(false);
                registerPanel.setVisible(false);
                repaint();
                requestFocusInWindow();
            }
            else if (button.equals("REGISTER")) {
                game.setState(GameState.REGISTER);
                loginPanel.setVisible(false);
                registerPanel.setVisible(true);
                registerPanel.requestFocusInWindow();
                repaint();
                System.out.println(game.getState());
            }
            else if (button.equals("EXIT")) {
                System.exit(0);
            }
            
            return;
        }
        if (game.getState() == GameState.GAME_OVER){
            String button = gameOverPanel.getClickedButtonGameOver(e.getX(), e.getY(), getWidth(), getHeight());
            if(button == null){
                return;
            }
            game.playClickSound();

            if(button.equals("PLAY_AGAIN")){
                game.resetGame();
            }
            else if(button.equals("MAIN_MENU")){
                game.setState(GameState.MENU);
            }

            return;
        }

        if(game.getState() == GameState.PAUSE){
            String button = pausePanel.getClickedPause(e.getX(), e.getY(), getWidth(), getHeight());
            if(button == null){
                return;
            }
            game.playClickSound();

            if (button.equals("PLAY_AGAIN")){
                game.resetGame();
            }
            else if(button.equals("MAIN_MENU")){
                game.setState(GameState.MENU);
            }
            else if (button.equals("CONTINUE")){
                game.setState(GameState.READY);
            }

            return;
        }

        if(game.getState() == GameState.RANKING){
            String button = rankingPanel.getClickedRanking(e.getX(), e.getY(), getWidth(), getHeight());
            if(button == null){
                return;
            }
            game.playClickSound();

            if (button.equals("MAIN_MENU")){
                hideRankingScrollPane();
                game.setState(GameState.MENU);
            }

            return;
        }
        if (game.getState() == GameState.SKINS) {
        // CATEGORY
        String category = skinPanel.getClickedCategory(e.getX(), e.getY(), getWidth(), getHeight());

        if (category != null) {
            skinPanel.setSelectedCategory(category);
            game.playClickSound();
            repaint();
            return;
        }
        // SKIN
        Skin clickedSkin = skinPanel.getClickedSkin(e.getX(), e.getY(), getWidth(), getHeight());

        if (clickedSkin != null) {
            game.selectSkin(clickedSkin);
            skinPanel.setSelectedSkin(clickedSkin);
            game.playClickSound();
            repaint();
            return;
        }
        // BACK
        if (skinPanel.isBackClicked(e.getX(), e.getY(), getWidth(), getHeight())) {
            game.setState(GameState.MENU);
            game.playClickSound();
            repaint();
            return;
        }
    }
        game.jump();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
/*Timer.start()
     ↓
chờ 16ms
     ↓
gọi actionPerformed(...)
     ↓
e -> {
    bird.update();
    repaint();
}
     ↓
chờ 16ms
     ↓
gọi actionPerformed(...)
     ↓
e -> {
    bird.update();
    repaint();
}
     ↓
chờ 16ms
     ↓ */