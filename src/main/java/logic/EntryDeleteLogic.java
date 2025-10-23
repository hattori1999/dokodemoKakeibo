package logic;

import dao.EntryDAO;

public class EntryDeleteLogic {

    // 削除処理を実行
    public boolean execute(int entryId) {
        EntryDAO dao = new EntryDAO();
        boolean deleted = dao.deleteById(entryId);

        if (deleted) {
            System.out.println("Entry ID " + entryId + " を削除しました");
        }
        return deleted;
    }
}
