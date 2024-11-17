package vn.edu.hust.studentman

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import vn.edu.hust.studentman.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    addEvents()
  }

  private fun addEvents() {
    binding.btnAddNew.setOnClickListener {
      val name = binding.editName.text.toString()
      val mssv = binding.editMssv.text.toString()

      students.add(StudentModel(name, mssv))
      Render()
    }

    binding.btnDelete.setOnClickListener {
      val name = binding.editName.text.toString()
      val mssv = binding.editMssv.text.toString()

      val student = students.find { it.studentId == mssv }
      if (student != null) {
        showDeleteDialog(student)
      }
    }

    binding.btnEdit.setOnClickListener {
      val name = binding.editName.text.toString()
      val mssv = binding.editMssv.text.toString()

      val student = students.find { it.studentId == mssv }
      if (student != null) {
        showEditDialog(student)
      }
    }
    Render()
  }

  private fun showEditDialog(student: StudentModel) {
    val dialogView = layoutInflater.inflate(R.layout.dialog_edit_student, null)
    val editName = dialogView.findViewById<EditText>(R.id.editNameDialog)
    val editMssv = dialogView.findViewById<EditText>(R.id.editMssvDialog)
    editName.setText(student.studentName)
    editMssv.setText(student.studentId)
    AlertDialog.Builder(this)
      .setTitle("Chỉnh sửa thông tin sinh viên")
      .setView(dialogView)
      .setPositiveButton("Cập nhật") { dialog, _ ->
        val name = editName.text.toString()
        val mssv = editMssv.text.toString()
        student.studentName = name
        student.studentId = mssv
        Render()
        dialog.dismiss()
      }
      .setNegativeButton("Hủy") { dialog, _ ->
        dialog.dismiss()
      }
      .create()
      .show()
  }


  private fun showDeleteDialog(student: StudentModel) {
    AlertDialog.Builder(this)
      .setTitle("Xóa sinh viên")
      .setMessage("Bạn có chắc chắn muốn xóa sinh viên này?")
      .setPositiveButton("Xóa") { dialog, _ ->
        students.remove(student)
        Render()
        showSnackbar(student)
        dialog.dismiss()
      }
      .setNegativeButton("Hủy") { dialog, _ ->
        dialog.dismiss()
      }
      .create()
      .show()
  }

  private fun showSnackbar(student: StudentModel) {
    val snackbar = Snackbar.make(binding.root, "Sinh viên ${student.studentName} đã bị xóa", Snackbar.LENGTH_LONG)
    snackbar.setAction("Hoàn tác") {
      students.add(student)
      Render()
    }
    snackbar.show()
  }

  private fun Render() {
    val studentAdapter = StudentAdapter(students)

    val rv = findViewById<RecyclerView>(R.id.rvRender)
    rv.layoutManager = LinearLayoutManager(this)
    rv.adapter = studentAdapter
  }
}
