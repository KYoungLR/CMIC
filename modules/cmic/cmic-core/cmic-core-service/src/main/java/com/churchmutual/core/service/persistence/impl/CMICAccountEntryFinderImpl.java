package com.churchmutual.core.service.persistence.impl;

import com.churchmutual.core.model.CMICAccountEntry;
import com.churchmutual.core.model.impl.CMICAccountEntryImpl;
import com.churchmutual.core.service.persistence.CMICAccountEntryFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class CMICAccountEntryFinderImpl extends CMICAccountEntryFinderBaseImpl implements CMICAccountEntryFinder {

	@Override
	public List<CMICAccountEntry> findByUserIdOrderedByName(long userId, int start, int end) {
		List<CMICAccountEntry> list = null;

		Session session = null;

		try {
			session = openSession();

			String sql = customSQL.get(
				getClass(), _GET_CMIC_ACCOUNT_ENTRIES_BY_USER_ID_ORDERED_BY_NAME);

			sql = StringUtil.replace(sql, "[$USER_ID$]", String.valueOf(userId));

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("cmic_CMICAccountEntry", CMICAccountEntryImpl.class);

			list = (List<CMICAccountEntry>) QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);

			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return list;
	}

	private static final String _GET_CMIC_ACCOUNT_ENTRIES_BY_USER_ID_ORDERED_BY_NAME =
		CMICAccountEntryFinder.class.getName() + ".getCMICAccountEntriesByUserIdOrderedByName";

	private static Log _log = LogFactoryUtil.getLog(CMICAccountEntryFinderImpl.class);

	@Reference
	protected CustomSQL customSQL;

}
