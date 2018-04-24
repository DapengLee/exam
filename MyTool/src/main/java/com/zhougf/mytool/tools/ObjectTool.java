package com.zhougf.mytool.tools;

import java.lang.reflect.Field;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ObjectTool {

	public static void putObject(SharedPreferences sp, Object obj) {
		Editor edit = sp.edit();
		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			System.out.println("putObject.type:" + field.getType());
			if (field.getType() == Integer.TYPE) {
				int value = -1;
				try {
					value = field.getInt(obj);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				// if (value > -1) {
				edit.putInt(field.getName(), value);
				// }
			} else if (field.getType() == String.class) {
				String value = null;
				try {
					value = String.valueOf(field.get(obj));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				// if (!TextUtils.isEmpty(value)) {
				edit.putString(field.getName(), value);
				// }
			} else if (field.getType() == boolean.class) {
				boolean value = false;
				try {
					value = Boolean.valueOf(field.getBoolean(obj));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				System.out.println("putObject.name:" + field.getName()
						+ "  value:" + value);
				edit.putBoolean(field.getName(), value);
			}
		}
		edit.commit();
	}

	public static <T> T getObject(SharedPreferences sp, Class<T> clazz) {
		T obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if (null != obj) {
			for (Field field : clazz.getDeclaredFields()) {
				field.setAccessible(true);
				System.out.println("getObject1.type:" + field.getType());
				if (field.getType() == String.class) {
					String value = sp.getString(field.getName(), "");
					try {
						field.set(obj, String.valueOf(value));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else if (field.getType() == Integer.TYPE) {
					int value = sp.getInt(field.getName(), -1);
					try {
						field.set(obj, value);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else if (field.getType() == Boolean.TYPE) {
					boolean value = sp.getBoolean(field.getName(), false);
					try {
						System.out.println("getObject1.name:" + field.getName()
								+ "  value:" + value);
						field.set(obj, value);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return obj;
	}

	public static <T> T getObject(SharedPreferences sp, T obj) {
		if (null != obj) {
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				System.out.println("getObject2.type:" + field.getType());
				if (field.getType() == String.class) {
					String value = sp.getString(field.getName(), "");
					try {
						field.set(obj, String.valueOf(value));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else if (field.getType() == Integer.TYPE) {
					int value = sp.getInt(field.getName(), -1);
					try {
						field.set(obj, value);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else if (field.getType() == Boolean.TYPE) {
					boolean value = sp.getBoolean(field.getName(), false);
					try {
						System.out.println("getObject2.name:" + field.getName()
								+ "  value:" + value);
						field.set(obj, value);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return obj;
	}
}
