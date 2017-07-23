package common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("activity", "activity_id", Activity.class);
		arp.addMapping("article", "art_id", Article.class);
		arp.addMapping("postbar", "postbar_id", Postbar.class);
		// Composite Primary Key order: comment_time,post_id,user_id
		arp.addMapping("postbar_comment", "comment_time,post_id,user_id", PostbarComment.class);
		arp.addMapping("que_answer", "que_id", QueAnswer.class);
		arp.addMapping("question", "que_id", Question.class);
		arp.addMapping("teacher", "teacher_id", Teacher.class);
		arp.addMapping("user_appointment", "appointment_id", UserAppointment.class);
		arp.addMapping("user_information", "id", UserInformation.class);
	}
}

