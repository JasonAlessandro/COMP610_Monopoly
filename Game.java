package monopoly;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Game {

    private static int currentPlayer = 0;

    public static void removePlayer(ArrayList<Player> players, int playerNum, Property[] properties) {
        // property
        for (int i = 0; i < 20; i++) {
            if (properties[i].getOwner() == players.get(playerNum)) {
                properties[i].setOwner(null);
            }
        }

        players.remove(playerNum);

        if (!players.isEmpty()) {
            currentPlayer = currentPlayer % players.size();
            players.get(currentPlayer).setHasEndedTurn(false);
        }
    }

    public static void checkRemovePlayer(ArrayList<Player> players, Property[] properties) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getProperty().getMoney() <= 0) {
                JFrame lossWindow = new JFrame("Player Lost");
            lossWindow.setSize(300, 200);
            lossWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Create a JLabel with the loss message and add it to the JFrame
            JLabel lossMessage = new JLabel(players.get(i).getName() + " has lost!");
            lossMessage.setHorizontalAlignment(JLabel.CENTER);
            lossWindow.add(lossMessage);

            // Display the JFrame
            lossWindow.setVisible(true);
                removePlayer(players, i, properties);
                break;
            }
        }
    }

   
    public static void checkWinner(ArrayList<Player> players) {
    // If there's only one player left, they are the winner
    if (players.size() == 1) {
        // Create a new JFrame and set its properties
        JFrame winWindow = new JFrame("Winner");
        winWindow.setSize(300, 200);
        winWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a JLabel with the win message and add it to the JFrame
        JLabel winMessage = new JLabel(players.get(0).getName() + " has won!");
        winMessage.setHorizontalAlignment(JLabel.CENTER);
        winWindow.add(winMessage);

        // Display the JFrame
        winWindow.setVisible(true);

        FileIO.incrementGamesPlayed();
    }
}


   public static void checkTurn(int turns, ArrayList<Player> players, Property[] properties) {
    // After each turn, check if any player should be removed, and if there's a winner
    checkRemovePlayer(players, properties);
    checkWinner(players);
}
   
 public static void initializeDatabase(int numPlayer) {
    try {
        String URL = "jdbc:derby://localhost:1527/Player";
        Connection connection = DriverManager.getConnection(URL);
        
        // 清空表中的数据
        String deleteSQL = "DELETE FROM APP.PLAYER";
        Statement deleteStatement = connection.createStatement();
        deleteStatement.executeUpdate(deleteSQL);
        deleteStatement.close();

       String insertSQL = "INSERT INTO APP.PLAYER (playernum, hasendturn, position, money) VALUES (?, ?, ?, ?)";
PreparedStatement insertStatement = connection.prepareStatement(insertSQL);

for (int i = 1; i <= numPlayer; i++) {
    insertStatement.setInt(1, i);
    insertStatement.setBoolean(2, false);
    insertStatement.setInt(3, 0); // 设置初始位置为0
    insertStatement.setInt(4, 1500); // 设置初始金额为1500
    insertStatement.executeUpdate();
}

for (int i = numPlayer+1; i <= 4; i++) {
    insertStatement.setInt(1, i);
    insertStatement.setBoolean(2, false);
    insertStatement.setInt(3, 0); // 设置初始位置为0
    insertStatement.setInt(4, 0); // 设置初始金额为1500
    insertStatement.executeUpdate();
}

// 清空 GAME_STATE 表中的数据
String deleteGameStateSQL = "DELETE FROM APP.STATE";
Statement deleteGameStateStatement = connection.createStatement();
deleteGameStateStatement.executeUpdate(deleteGameStateSQL);
deleteGameStateStatement.close();

// 初始化currentPlayer
String insertGameStateSQL = "INSERT INTO APP.STATE (currentPlayer) VALUES (?)";
PreparedStatement insertGameStateStatement = connection.prepareStatement(insertGameStateSQL);
insertGameStateStatement.setInt(1, 1);
insertGameStateStatement.executeUpdate();
insertGameStateStatement.close();

insertStatement.close();




        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



public static void saveGame(ArrayList<Player> players, int id,int currentPlayer) {
    try {
        String URL = "jdbc:derby://localhost:1527/Player";
        Connection connection = DriverManager.getConnection(URL);
        String updateSQL = "UPDATE APP.PLAYER SET position = ?, hasendturn = ?, money = ? WHERE playernum = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateSQL);
        
        // 对所有玩家进行处理
        for (Player player : players) {
            int playerNum = players.indexOf(player);
            updateStatement.setInt(1, Monopoly.playerPositions[playerNum]);
            updateStatement.setBoolean(2, player.getHasEndedTurn());
            updateStatement.setInt(3, player.getProperty().getMoney());
            updateStatement.setInt(4, playerNum); 
            updateStatement.executeUpdate();
        }
        
         // 更新当前玩家的信息
        String updateCurrentPlayerSQL = "UPDATE APP.STATE SET currentPlayer = ?";
        PreparedStatement updateCurrentPlayerStatement = connection.prepareStatement(updateCurrentPlayerSQL);
        updateCurrentPlayerStatement.setInt(1, currentPlayer); 
        updateCurrentPlayerStatement.executeUpdate();

        updateStatement.close();
        updateCurrentPlayerStatement.close();
        updateStatement.close();

        // 关闭连接
        connection.close();
    } catch (SQLException e) {
        System.out.println("SQLException: " + e.getMessage());
        e.printStackTrace();
    }
}



public static void continuePlaying() {
    try {
        // 连接数据库
        String URL = "jdbc:derby://localhost:1527/Player";
        Connection connection = DriverManager.getConnection(URL);

        // 查询并加载玩家数据
        // 查询并加载玩家数据
        String query = "SELECT * FROM APP.PLAYER"; // 根据你的数据库表名修改查询语句
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

    // 处理查询结果，更新游戏状态
        for (int i = 0; i < 4; i++) {
        int playerNum = i + 1;

    // 查询特定玩家的数据
    String playerQuery = "SELECT * FROM APP.PLAYER WHERE PlayerNum = " + playerNum; // 根据你的数据库表名和列名修改查询语句
    ResultSet playerResultSet = statement.executeQuery(playerQuery);

    // 检查查询结果是否有效
    if (playerResultSet.next()) {
        int playerMoney = playerResultSet.getInt("money");
        int playerPosition = playerResultSet.getInt("Position");
         boolean hasEndTurn = playerResultSet.getBoolean("HasEndTurn");

        // 根据玩家数据创建Player对象并添加到players列表
        Player player = new Player("Player " + playerNum, Monopoly.playerColors[i], playerMoney,hasEndTurn,playerNum);
        Monopoly.players.add(player);

        // 更新玩家位置
        Monopoly.playerPositions[i] = playerPosition;
    } else {
        // 玩家数据不存在或无效，从游戏中移除该玩家
        Monopoly.players.remove(i);
        Monopoly.playerPositions[i] = 0;
    }
    
    String currentPlayerQuery = "SELECT currentPlayer FROM APP.STATE";
ResultSet currentPlayerResultSet = statement.executeQuery(currentPlayerQuery);
if (currentPlayerResultSet.next()) {
    Monopoly.currentPlayer = currentPlayerResultSet.getInt("currentPlayer");
}

    // 关闭玩家查询结果集
    playerResultSet.close();
}

// 关闭连接
resultSet.close();
statement.close();
connection.close();



    } catch (SQLException e) {
        e.printStackTrace();
    }
}
   

}
