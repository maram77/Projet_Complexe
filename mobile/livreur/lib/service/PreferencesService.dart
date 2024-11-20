import 'package:shared_preferences/shared_preferences.dart';

class PreferencesService {
  Future<void> saveUserAttributes(String userId, String token) async {
    final preferences = await SharedPreferences.getInstance();
    await preferences.setString('userId', userId);
    await preferences.setString('token', token);
  }

  Future<Map<String, String>> getUserAttributes() async {
    final preferences = await SharedPreferences.getInstance();
    String userId = preferences.getString('userId') ?? '';
    String token = preferences.getString('token') ?? '';
    return {'userId': userId, 'token': token};
  }

  Future<void> deleteUserAttributes() async {
    final preferences = await SharedPreferences.getInstance();
    await preferences.remove('userId');
    await preferences.remove('token');
  }
}
