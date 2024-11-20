class User {
  final int? id;
  final String? name;  // Add other fields as necessary

  User({this.id, this.name});

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'] as int?,
      name: json['name'] as String?,
    );
  }
}
