package logic;

import dao.EntryDAO;
import model.Entry;

public class EntryLogic {
	
	private EntryDAO entryDAO;
	
	public EntryLogic() {
		this.entryDAO = new EntryDAO();
	}
	
	//idで一件の記帳を取得
	public Entry getEntryById(int entryId) {
		Entry entry = entryDAO.findById(entryId);
		
		if(entry == null) {
			System.out.println("記帳データは存在しません: entryId=" + entryId);
		}
		
		return entry;
	}
	
	/**
     * 指定されたentryIdを削除する
     * @param entryId 削除対象のID
     * @return 削除成功ならtrue
     */
    public boolean execute(int entryId) {
        // 1. 事前条件チェック（存在確認など）
        EntryDAO dao = new EntryDAO();
        // ここで存在確認や権限チェックを入れることも可能
        // 例: if (!dao.exists(entryId)) return false;

        // 2. 削除実行
        boolean deleted = dao.deleteById(entryId);

        // 3. 削除後処理（ログ記録など）
        if (deleted) {
            System.out.println("Entry ID " + entryId + " を削除しました");
            // 必要なら履歴テーブルに記録することも可能
        }

        return deleted;
    }
}
