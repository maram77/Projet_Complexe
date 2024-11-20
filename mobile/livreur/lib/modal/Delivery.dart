class Delivery {
  final int? id;
  final String? address;
  final String? contactNumber;
  final String? companyName;
  final String? city;
  final String? state;
  final String? postCode;

  Delivery({this.id, this.address, this.contactNumber, this.companyName, this.city, this.state, this.postCode});

  factory Delivery.fromJson(Map<String, dynamic> json) {
    return Delivery(
      id: json['id'] as int?,
      address: json['address'] as String?,
      contactNumber: json['contactNumber'] as String?,
      companyName: json['companyName'] as String?,
      city: json['city'] as String?,
      state: json['state'] as String?,
      postCode: json['postCode'] as String?,
    );
  }
}
