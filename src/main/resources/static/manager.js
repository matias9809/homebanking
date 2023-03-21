const { createApp } = Vue
createApp( {
    data(){
        return {
            post:{
                firstname:"",
                lastname:"",
                email:"",
            },
            modal_modify:undefined,
            client:undefined,
        }
    },
    created(){
        axios.get(`http://localhost:8080/api/clients`)
        .then(response=>{
            this.client=response.data;
        })
        .catch(err=>console.log(err))
    },
    methods: {
        add_client:function(){
            axios.post(`http://localhost:8080/rest/clients`,{...this.post}).then((result) => {
                console.log(result);
                alert("se agrego correctamente")
                this.load_data();
                this.post.firstname="";
                this.post.lastname="";
                this.post.email="";
            });
        },
        load_data:function(){
            axios.get(`http://localhost:8080/api/clients`)
            .then(response=>{
                this.client=response.data;
            })
            .catch(err=>console.log(err))
        },
        delete_client:function(cli){
                console.log(cli)
                axios.delete(`http://localhost:8080/rest/clients/`+cli.id)
                .then(response=>{
                    this.load_data();
                })
        },
        modal_modif:function(cli){
            this.modal_modify={...cli};
            console.log(this.modal_modify)
        },
        modify_data:function(){
            axios.put(this.modal_modify._links.self.href,this.modal_modify)
            .then(response=>{
                this.load_data();
            })
            .catch(err=>console.log(err))
        }
    },

} ).mount("#app")