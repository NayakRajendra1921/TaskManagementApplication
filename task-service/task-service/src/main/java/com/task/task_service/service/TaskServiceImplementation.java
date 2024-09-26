package com.task.task_service.service;

import com.task.task_service.model.Task;
import com.task.task_service.model.TaskStatus;
import com.task.task_service.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImplementation implements TaskService{

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task, String requestRole) throws Exception {
        if(!requestRole.equals("ROLE_ADMIN"))
            throw new Exception("only admin can create task");

        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) throws Exception {
        return taskRepository.findById(id).orElseThrow(()->new Exception("Task not found with id "+id));
    }

    @Override
    public List<Task> getAllTask(TaskStatus status) {
        List<Task> filteredTask=taskRepository.findAll().stream().filter(task->
                status==null||task.getStatus().getValue().equals(status.getValue())).toList();

        return filteredTask;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask, Long userId) throws Exception {

        if(!taskRepository.existsById(updatedTask.getId()))
            throw new Exception("Task doesn't exists by the provided task Id");

        Task existsingTask=taskRepository.findById(id).get();

        if(updatedTask.getStatus()!=null){
            existsingTask.setStatus(updatedTask.getStatus());
        }
        if(updatedTask.getDescription()!=null){
            existsingTask.setDescription(updatedTask.getDescription());
        }
        if(updatedTask.getImage()!=null){
            existsingTask.setImage(updatedTask.getImage());
        }
        if(updatedTask.getTitle()!=null){
            existsingTask.setTitle(updatedTask.getTitle());
        }
        if(updatedTask.getDeadLine()!=null){
            existsingTask.setDeadLine(updatedTask.getDeadLine());
        }


        return taskRepository.save(existsingTask);
    }

    @Override
    public void deleteTask(Long id) throws Exception{
        if(!taskRepository.existsById(id))
            throw new Exception("No task found with given ID");

        taskRepository.deleteById(id);
    }

    @Override
    public Task assignedToUser(Long userId, Long taskId) throws Exception {
        Task task=getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.DONE);

        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUsersTask(Long userId, TaskStatus status) {

        List<Task> alltask=taskRepository.findByAssignedUserId(userId).stream().filter(task->
                status==null||task.getStatus().getValue().equals(status.getValue())).toList();

        return List.of();
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {
        Task task=getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}
